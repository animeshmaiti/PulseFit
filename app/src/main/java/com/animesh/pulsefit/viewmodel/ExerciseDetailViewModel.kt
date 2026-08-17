package com.animesh.pulsefit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.UserProfileRepository
import com.animesh.pulsefit.ui.exercise.details.SessionState
import com.animesh.pulsefit.viewmodel.event.WorkoutSound
import com.animesh.pulsefit.viewmodel.utils.toHmsString
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ExerciseDetailViewModel(
    private val repository: ExerciseRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    var exercise by mutableStateOf<Exercise?>(null)
        private set

    private var timerJob: Job? = null

    var sessionState by mutableStateOf(SessionState.SETUP)
        private set

    var selectedDuration by mutableIntStateOf(30)
        private set

    var remainingTime by mutableIntStateOf(0)
        private set

    var totalTime by mutableIntStateOf(0)
        private set

    private var weightKg by mutableFloatStateOf(0f)

    private val _deleted = Channel<Unit>()

    val deleted = _deleted.receiveAsFlow()

    private val _sound = Channel<WorkoutSound>()

    val sound = _sound.receiveAsFlow()

    var displayText by mutableStateOf(
        selectedDuration.toHmsString()
    )
        private set

    fun loadExercise(exerciseId: Long) {

        viewModelScope.launch {

            exercise =
                repository.getExerciseById(exerciseId)

            selectedDuration =
                exercise?.defaultDuration ?: 30

            displayText =
                selectedDuration.toHmsString()

            val profile =
                userProfileRepository.getProfile()

            weightKg =
                profile?.weightKg ?: 0f
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

    /**
     * Calories burned during the current session.
     *
     * Formula:
     *
     * kcal = MET × weight(kg) × time(hours)
     */
    val caloriesBurned: Float
        get() {

            val met =
                exercise?.met ?: 0f

            if (met <= 0f || weightKg <= 0f) {
                return 0f
            }

            val elapsedSeconds =
                when (sessionState) {

                    SessionState.SETUP,
                    SessionState.COUNTDOWN -> 0

                    SessionState.RUNNING,
                    SessionState.PAUSED ->
                        selectedDuration - remainingTime

                    SessionState.FINISHED ->
                        totalTime
                }

            val hours =
                elapsedSeconds / 3600f

            return met * weightKg * hours
        }

    val hasCaloriesData: Boolean
        get() =
            (exercise?.met ?: 0f) > 0f &&
                    weightKg > 0f

    private suspend fun runTimer() {

        while (
            remainingTime >= 0 &&
            sessionState == SessionState.RUNNING
        ) {

            displayText =
                remainingTime.toHmsString()

            if (remainingTime == 0) {
                break
            }

            delay(1000)

            remainingTime--
        }

        finishSessionInternal()
    }

    fun startSession() {

        if (sessionState != SessionState.SETUP) {
            return
        }

        sessionState =
            SessionState.COUNTDOWN

        timerJob?.cancel()

        timerJob =
            viewModelScope.launch {

                displayText = "3"
                _sound.send(
                    WorkoutSound.COUNTDOWN
                )
                delay(1000)

                displayText = "2"
                _sound.send(
                    WorkoutSound.COUNTDOWN
                )
                delay(1000)

                displayText = "1"
                _sound.send(
                    WorkoutSound.COUNTDOWN
                )
                delay(1000)

                displayText = "GO!"
                _sound.send(
                    WorkoutSound.GO
                )
                delay(700)

                sessionState =
                    SessionState.RUNNING

                remainingTime =
                    selectedDuration

                runTimer()
            }
    }

    fun pauseSession() {

        if (sessionState != SessionState.RUNNING) {
            return
        }

        timerJob?.cancel()

        sessionState =
            SessionState.PAUSED
    }

    fun resumeSession() {

        if (sessionState != SessionState.PAUSED) {
            return
        }

        sessionState =
            SessionState.RUNNING

        timerJob?.cancel()

        timerJob =
            viewModelScope.launch {
                runTimer()
            }
    }

    private suspend fun finishSessionInternal() {

        timerJob = null

        totalTime =
            selectedDuration - remainingTime

        sessionState =
            SessionState.FINISHED

        _sound.send(
            WorkoutSound.FINISH
        )
    }

    fun finishSession() {

        timerJob?.cancel()

        viewModelScope.launch {
            finishSessionInternal()
        }
    }

    fun cancelSession() {

        timerJob?.cancel()

        timerJob = null

        remainingTime = 0

        totalTime = 0

        displayText =
            selectedDuration.toHmsString()

        sessionState =
            SessionState.SETUP
    }

    fun updateDuration(seconds: Int) {

        selectedDuration = seconds

        if (sessionState == SessionState.SETUP) {

            displayText =
                seconds.toHmsString()
        }
    }

    fun reset() {

        timerJob?.cancel()

        timerJob = null

        sessionState =
            SessionState.SETUP

        selectedDuration =
            exercise?.defaultDuration ?: 30

        remainingTime = 0

        totalTime = 0

        displayText =
            selectedDuration.toHmsString()
    }

    companion object {

        fun factory(
            exerciseRepository: ExerciseRepository,
            userProfileRepository: UserProfileRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {

                    return ExerciseDetailViewModel(
                        exerciseRepository,
                        userProfileRepository
                    ) as T
                }
            }
    }
}