package com.animesh.pulsefit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AddExerciseViewModel(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _message = Channel<String>(Channel.BUFFERED)
    val message = _message.receiveAsFlow()

    suspend fun createExercise(exercise: Exercise): Boolean {
        if (exerciseRepository.exerciseExists(exercise.name)) {
            return false
        }

        exerciseRepository.createExercise(exercise)
        _message.send("Exercise saved successfully.")
        return true
    }

    companion object {

        fun factory(
            exerciseRepository: ExerciseRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return AddExerciseViewModel(
                        exerciseRepository
                    ) as T
                }
            }
    }
}