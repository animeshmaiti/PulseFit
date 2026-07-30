package com.animesh.pulsefit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ExerciseDetailViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

    var exercise by mutableStateOf<Exercise?>(null)
        private set
    private val _deleted = Channel<Unit>()
    val deleted = _deleted.receiveAsFlow()
    fun loadExercise(exerciseId: Long) {
        viewModelScope.launch {
            exercise = repository.getExerciseById(exerciseId)
        }
    }

    fun deleteExercise() {
        viewModelScope.launch {

            exercise?.let {
                repository.deleteExercise(it)
                _deleted.send(Unit)
            }
        }
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
                    return ExerciseDetailViewModel(exerciseRepository) as T
                }
            }
    }
}