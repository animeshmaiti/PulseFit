package com.animesh.pulsefit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.entity.Workout
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExerciseViewModel(
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
    val workouts: StateFlow<List<Workout>> =
        workoutRepository
            .getAllWorkouts()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.deleteExercise(exercise)
        }
    }

    fun toggleFavoriteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.toggleFavorite(exercise)
        }
    }
    fun toggleFavoriteWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.toggleFavorite(workout)
        }
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
                    return ExerciseViewModel(exerciseRepository,workoutRepository) as T
                }
            }
    }
}