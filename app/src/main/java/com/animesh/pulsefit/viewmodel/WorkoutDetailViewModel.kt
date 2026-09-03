package com.animesh.pulsefit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Workout
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutBuilderRepository
import com.animesh.pulsefit.data.repository.WorkoutExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WorkoutDetailViewModel(
    private val workoutRepository: WorkoutRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository,
    private val exerciseRepository: ExerciseRepository,
    private val workoutBuilderRepository: WorkoutBuilderRepository
) : ViewModel() {
    data class WorkoutExerciseUi(
        val exerciseName: String,
        val duration: Int,
        val breakDuration: Int
    )
    var workout by mutableStateOf<Workout?>(null)
        private set

    var exercises by mutableStateOf<List<WorkoutExerciseUi>>(emptyList())
        private set

    var exerciseCount by mutableIntStateOf(0)
        private set

    var totalExerciseDuration by mutableIntStateOf(0)
        private set

    var totalBreakDuration by mutableIntStateOf(0)
        private set

    val totalDuration: Int
        get() = totalExerciseDuration + totalBreakDuration


    fun loadWorkout(workoutId: Long) {

        viewModelScope.launch {

            workout =
                workoutRepository.getWorkoutById(workoutId)

            workoutExerciseRepository
                .getExercisesForWorkout(workoutId)
                .collectLatest { workoutExercises ->

                    exercises =
                        workoutExercises.mapNotNull { workoutExercise ->

                            val exercise =
                                exerciseRepository.getExerciseById(
                                    workoutExercise.exerciseId
                                )

                            exercise?.let {

                                WorkoutExerciseUi(
                                    exerciseName = it.name,
                                    duration = workoutExercise.duration,
                                    breakDuration =
                                    workoutExercise.breakDuration
                                )
                            }
                        }

                    exerciseCount =
                        exercises.size

                    totalExerciseDuration =
                        exercises.sumOf { it.duration }

                    totalBreakDuration =
                        exercises.sumOf { it.breakDuration }
                }
        }
    }
    suspend fun deleteWorkout() {
        val currentWorkout = workout ?: return

        workoutBuilderRepository.deleteWorkout(
            currentWorkout
        )
    }
    companion object {

        fun factory(
            workoutRepository: WorkoutRepository,
            workoutExerciseRepository: WorkoutExerciseRepository,
            exerciseRepository: ExerciseRepository,
            workoutBuilderRepository: WorkoutBuilderRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {

                    return WorkoutDetailViewModel(
                        workoutRepository,
                        workoutExerciseRepository,
                        exerciseRepository,
                        workoutBuilderRepository
                    ) as T
                }
            }
    }
}