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