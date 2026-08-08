package com.animesh.pulsefit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditExerciseViewModel(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    var exercise: Exercise? = null
        private set

    private val _updated = Channel<Unit>()
    val updated = _updated.receiveAsFlow()

    fun loadExercise(id: Long) {

        viewModelScope.launch {

            exercise =
                exerciseRepository.getExerciseById(id)
        }
    }

    suspend fun updateExercise(
        exercise: Exercise
    ): Boolean {

        val existing = this.exercise ?: return false

        // Don't allow another exercise to have
        // the same name as this one.
        val duplicate =
            exerciseRepository.exerciseExists(exercise.name)

        if (duplicate && exercise.name.lowercase() != existing.name.lowercase()) {
            return false
        }

        exerciseRepository.updateExercise(
            exercise.copy(
                id = existing.id,
                isBuiltIn = existing.isBuiltIn
            )
        )

        _updated.send(Unit)

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
                    return EditExerciseViewModel(
                        exerciseRepository
                    ) as T
                }
            }
    }
}