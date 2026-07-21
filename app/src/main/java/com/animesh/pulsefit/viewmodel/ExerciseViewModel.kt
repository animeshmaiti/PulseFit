package com.animesh.pulsefit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExerciseViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

    val exercises: StateFlow<List<Exercise>> =
        repository
            .getAllExercises()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    private val _message = MutableSharedFlow<String>(
        replay = 1
    )
    val message = _message.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clearMessage() {
        _message.resetReplayCache()
    }

    fun createExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.createExercise(exercise)
            _message.emit("Exercise saved successfully.")
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.deleteExercise(exercise)
        }
    }

    fun toggleFavorite(exercise: Exercise) {
        viewModelScope.launch {
            repository.toggleFavorite(exercise)
        }
    }

    companion object {

        fun factory(
            repository: ExerciseRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return ExerciseViewModel(repository) as T
                }
            }
    }
}