package com.animesh.pulsefit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutRepository
import com.animesh.pulsefit.ui.exercise.create.model.WorkoutExerciseUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AddWorkoutViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val workoutRepository: WorkoutRepository
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

    val selectedExercises = mutableStateListOf<WorkoutExerciseUi>()

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
    companion object {

        fun factory(
            exerciseRepository: ExerciseRepository,
            workoutRepository: WorkoutRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return AddWorkoutViewModel(
                        exerciseRepository,
                        workoutRepository
                    ) as T
                }
            }
    }
}