package com.animesh.pulsefit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.entity.Workout
import com.animesh.pulsefit.data.entity.WorkoutExercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutBuilderRepository
import com.animesh.pulsefit.data.repository.WorkoutExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutRepository
import com.animesh.pulsefit.ui.exercise.create.model.WorkoutExerciseUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddWorkoutViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository,
    private val workoutBuilderRepository: WorkoutBuilderRepository
) : ViewModel() {

    val exercises: StateFlow<List<Exercise>> =
        exerciseRepository
            .getAllExercises()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    var workoutName by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _message =
        Channel<String>()

    val message =
        _message.receiveAsFlow()

    fun reset() {
        workoutName = ""
        description = ""
        selectedExercises.clear()

        editingWorkoutId = null
        editingWorkout = null
        loadedWorkoutId = null
    }

    val selectedExercises = mutableStateListOf<WorkoutExerciseUi>()

    private var editingWorkoutId: Long? = null

    fun onWorkoutNameChanged(name: String) {
        workoutName = name
    }

    fun onDescriptionChanged(description: String) {
        this.description = description
    }

    fun toggleExercise(exercise: Exercise) {

        val existing =
            selectedExercises.firstOrNull {
                it.exercise.id == exercise.id
            }

        if (existing != null) {

            selectedExercises.remove(existing)

        } else {

            selectedExercises.add(
                WorkoutExerciseUi(
                    exercise = exercise
                )
            )
        }

        println(
            "SELECTED: ${
                selectedExercises.map {
                    it.exercise.name
                }
            }"
        )
    }

    fun isSelected(exercise: Exercise): Boolean {
        return selectedExercises.any {
            it.exercise.id == exercise.id
        }
    }

    fun removeExercise(exerciseId: Long) {

        selectedExercises.removeAll {
            it.exercise.id == exerciseId
        }

    }

    var isSaving by mutableStateOf(false)
        private set

    fun updateDuration(
        exerciseId: Long,
        duration: Int
    ) {

        val index = selectedExercises.indexOfFirst {
            it.exercise.id == exerciseId
        }

        if (index == -1) return

        selectedExercises[index] =
            selectedExercises[index].copy(
                duration = duration
            )
    }

    fun clearSelection() {
        selectedExercises.clear()
    }

    fun getTotalDuration(): Int =
        selectedExercises.sumOf { it.duration }

    private val canSaveWorkout: Boolean
        get() = workoutName.isNotBlank() &&
                selectedExercises.isNotEmpty()

    fun createWorkout() {

        if (!canSaveWorkout || isSaving) return

        viewModelScope.launch {

            isSaving = true

            try {

                val workout = Workout(
                    name = workoutName.trim(),
                    description = description.trim()
                )

                val workoutExercises =
                    selectedExercises.mapIndexed { index, item ->

                        WorkoutExercise(
                            workoutId = 0,
                            exerciseId = item.exercise.id,
                            duration = item.duration,
                            breakType = item.breakType,
                            breakDuration = item.breakDuration,
                            position = index
                        )
                    }

                workoutBuilderRepository.createWorkout(
                    workout,
                    workoutExercises
                )

                _message.send(
                    "Workout created successfully."
                )

                reset()

            } catch (e: Exception) {

                _message.send(
                    "Failed to create workout."
                )

            } finally {

                isSaving = false
            }
        }
    }

    fun updateWorkout() {

        if (!canSaveWorkout || isSaving) return

        val workoutId =
            editingWorkoutId ?: return

        val existingWorkout =
            editingWorkout ?: return

        viewModelScope.launch {

            isSaving = true

            try {

                val workout =
                    existingWorkout.copy(
                        name = workoutName.trim(),
                        description = description.trim()
                    )

                val workoutExercises =
                    selectedExercises.mapIndexed { index, item ->

                        WorkoutExercise(
                            workoutId = workoutId,
                            exerciseId = item.exercise.id,
                            duration = item.duration,
                            breakType = item.breakType,
                            breakDuration = item.breakDuration,
                            position = index
                        )
                    }

                workoutBuilderRepository.updateWorkout(
                    workout,
                    workoutExercises
                )

                _message.send(
                    "Workout updated successfully."
                )

                reset()

            } catch (e: Exception) {

                _message.send(
                    "Failed to update workout."
                )

            } finally {

                isSaving = false
            }
        }
    }

    private var loadedWorkoutId: Long? = null
    private var editingWorkout: Workout? = null
    fun loadWorkout(workoutId: Long) {
        println("LOAD WORKOUT: $workoutId")
        if (loadedWorkoutId == workoutId) {
            return
        }

        viewModelScope.launch {

            val workout =
                workoutRepository.getWorkoutById(workoutId)
                    ?: return@launch

            editingWorkout = workout

            workoutName = workout.name
            description = workout.description
            editingWorkoutId = workoutId

            val workoutExercises =
                workoutExerciseRepository
                    .getExercisesForWorkout(workoutId)
                    .first()

            selectedExercises.clear()

            workoutExercises.forEach { workoutExercise ->

                val exercise =
                    exerciseRepository.getExerciseById(
                        workoutExercise.exerciseId
                    ) ?: return@forEach

                selectedExercises.add(
                    WorkoutExerciseUi(
                        exercise = exercise,
                        duration = workoutExercise.duration,
                        breakType = workoutExercise.breakType,
                        breakDuration = workoutExercise.breakDuration
                    )
                )
            }

            loadedWorkoutId = workoutId
        }
    }

    companion object {

        fun factory(
            exerciseRepository: ExerciseRepository,
            workoutRepository: WorkoutRepository,
            workoutExerciseRepository: WorkoutExerciseRepository,
            workoutBuilderRepository: WorkoutBuilderRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return AddWorkoutViewModel(
                        exerciseRepository,
                        workoutRepository,
                        workoutExerciseRepository,
                        workoutBuilderRepository
                    ) as T
                }
            }
    }
}