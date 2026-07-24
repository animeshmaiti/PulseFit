package com.animesh.pulsefit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AddExerciseViewModel(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _message = MutableSharedFlow<String>(replay = 1)
    val message = _message.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clearMessage() {
        _message.resetReplayCache()
    }

    suspend fun createExercise(exercise: Exercise): Boolean {
        if (exerciseRepository.exerciseExists(exercise.name)) {
            return false
        }

        exerciseRepository.createExercise(exercise)
        _message.emit("Exercise saved successfully.")
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