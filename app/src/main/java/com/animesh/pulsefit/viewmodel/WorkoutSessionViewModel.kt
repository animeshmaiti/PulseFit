package com.animesh.pulsefit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.enums.BreakType
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.UserProfileRepository
import com.animesh.pulsefit.data.repository.WorkoutExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutRepository
import com.animesh.pulsefit.ui.exercise.create.model.WorkoutExerciseUi
import com.animesh.pulsefit.ui.exercise.details.WorkoutSessionState
import com.animesh.pulsefit.viewmodel.event.WorkoutSound
import com.animesh.pulsefit.viewmodel.utils.toHmsString
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WorkoutSessionViewModel(
    private val workoutRepository: WorkoutRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository,
    private val exerciseRepository: ExerciseRepository,
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    // ---------------------------------------------------------
    // Workout
    // ---------------------------------------------------------

    var workoutName by mutableStateOf("")
        private set

    var exercises by mutableStateOf<List<WorkoutExerciseUi>>(emptyList())
        private set

    // ---------------------------------------------------------
    // Current exercise
    // ---------------------------------------------------------

    var currentExerciseIndex by mutableIntStateOf(0)
        private set

    val currentExercise: WorkoutExerciseUi?
        get() = exercises.getOrNull(currentExerciseIndex)

    val exerciseNumber: Int
        get() = currentExerciseIndex + 1

    val exerciseCount: Int
        get() = exercises.size

    // ---------------------------------------------------------
    // Session state
    // ---------------------------------------------------------

    var sessionState by mutableStateOf(
        WorkoutSessionState.SETUP
    )
        private set

    // ---------------------------------------------------------
    // Timer
    // ---------------------------------------------------------

    private var timerJob: Job? = null

    var remainingTime by mutableIntStateOf(0)
        private set

    var displayText by mutableStateOf("0:00")
        private set

    // Time spent exercising only
    var totalTime by mutableIntStateOf(0)
        private set

    // Total session time including breaks
    private var totalSessionTime by mutableIntStateOf(0)

    // ---------------------------------------------------------
    // Calories
    // ---------------------------------------------------------

    private var weightKg by mutableFloatStateOf(0f)

    /**
     * Total calories burned during the workout.
     *
     * Calories are calculated only from exercise time,
     * not break time.
     */
    val caloriesBurned: Float
        get() {

            if (weightKg <= 0f) {
                return 0f
            }

            var calories = 0f

            exercises.forEachIndexed { index, exerciseItem ->

                val seconds: Int =
                    when {

                        index < currentExerciseIndex ->
                            exerciseItem.duration

                        index == currentExerciseIndex &&
                                sessionState != WorkoutSessionState.SETUP &&
                                sessionState != WorkoutSessionState.COUNTDOWN &&
                                sessionState != WorkoutSessionState.BREAK ->
                            exerciseItem.duration - remainingTime

                        index == currentExerciseIndex &&
                                sessionState == WorkoutSessionState.BREAK ->
                            exerciseItem.duration

                        else ->
                            0
                    }

                val met = exerciseItem.exercise.met

                if (met > 0f && seconds > 0) {

                    calories +=
                        met *
                                weightKg *
                                (seconds / 3600f)
                }
            }

            return calories
        }

    val hasCaloriesData: Boolean
        get() =
            weightKg > 0f &&
                    exercises.any {
                        it.exercise.met > 0f
                    }

    // ---------------------------------------------------------
    // Sound
    // ---------------------------------------------------------

    private val _sound = Channel<WorkoutSound>()

    val sound = _sound.receiveAsFlow()

    // ---------------------------------------------------------
    // Load workout
    // ---------------------------------------------------------

    fun loadWorkout(workoutId: Long) {

        viewModelScope.launch {

            val workout =
                workoutRepository.getWorkoutById(workoutId)
                    ?: return@launch

            workoutName = workout.name

            val profile =
                userProfileRepository.getProfile()

            weightKg =
                profile?.weightKg ?: 0f

            val workoutExercises =
                workoutExerciseRepository
                    .getExercisesForWorkout(workoutId)
                    .first()

            exercises =
                workoutExercises.mapNotNull { workoutExercise ->

                    val exercise =
                        exerciseRepository.getExerciseById(
                            workoutExercise.exerciseId
                        )

                    exercise?.let {

                        WorkoutExerciseUi(
                            exercise = it,
                            duration = workoutExercise.duration,
                            breakType = workoutExercise.breakType,
                            breakDuration =
                            workoutExercise.breakDuration
                        )
                    }
                }

            currentExerciseIndex = 0

            totalTime = 0
            totalSessionTime = 0

            remainingTime = 0

            displayText =
                "0:00"

            sessionState =
                WorkoutSessionState.SETUP
        }
    }

    // ---------------------------------------------------------
    // Start
    // ---------------------------------------------------------

    fun startSession() {

        if (
            sessionState != WorkoutSessionState.SETUP ||
            exercises.isEmpty()
        ) {
            return
        }

        currentExerciseIndex = 0
        totalTime = 0
        totalSessionTime = 0

        startCountdown()
    }

    // ---------------------------------------------------------
    // Countdown
    // ---------------------------------------------------------

    private fun startCountdown() {

        timerJob?.cancel()

        sessionState =
            WorkoutSessionState.COUNTDOWN

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

                startExercise()
            }
    }

    // ---------------------------------------------------------
    // Start current exercise
    // ---------------------------------------------------------

    private fun startExercise() {

        val exercise =
            currentExercise
                ?: return

        sessionState =
            WorkoutSessionState.RUNNING

        remainingTime =
            exercise.duration

        displayText =
            remainingTime.toHmsString()

        timerJob = viewModelScope.launch {
            runExerciseTimer()
        }
    }

    // ---------------------------------------------------------
    // Exercise timer
    // ---------------------------------------------------------

    private suspend fun runExerciseTimer() {

        while (
            remainingTime >= 0 &&
            sessionState == WorkoutSessionState.RUNNING
        ) {

            displayText =
                remainingTime.toHmsString()

            if (remainingTime == 0) {
                break
            }

            delay(1000)

            remainingTime--

            totalTime++
            totalSessionTime++
        }

        if (
            sessionState ==
            WorkoutSessionState.RUNNING
        ) {
            finishCurrentExercise()
        }
    }

    // ---------------------------------------------------------
    // Exercise finished
    // ---------------------------------------------------------

    private fun finishCurrentExercise() {

        timerJob = null

        // Last exercise
        if (currentExerciseIndex == exercises.lastIndex) {
            finishWorkout()
            return
        }

        // Show next exercise
        val nextExercise = exercises[currentExerciseIndex + 1]

        displayText = "Next ${nextExercise.exercise.name}"

        // Play exercise completion sound
        viewModelScope.launch {
            _sound.send(WorkoutSound.EXERCISE_COMPLETE)

            delay(2000)

            // Continue with break or next exercise
            val exercise = currentExercise ?: return@launch

            if (
                exercise.breakType == BreakType.TIMER &&
                exercise.breakDuration > 0
            ) {
                startBreak()
            } else {
                moveToNextExercise()
            }
        }
    }

    // ---------------------------------------------------------
    // Break
    // ---------------------------------------------------------

    private fun startBreak() {

        val exercise =
            currentExercise
                ?: return

        remainingTime =
            exercise.breakDuration

        displayText =
            remainingTime.toHmsString()

        sessionState =
            WorkoutSessionState.BREAK

        timerJob?.cancel()

        timerJob =
            viewModelScope.launch {

                while (
                    remainingTime >= 0 &&
                    sessionState ==
                    WorkoutSessionState.BREAK
                ) {

                    displayText =
                        remainingTime.toHmsString()

                    if (remainingTime == 0) {
                        break
                    }

                    delay(1000)

                    remainingTime--

                    totalSessionTime++
                }

                if (
                    sessionState ==
                    WorkoutSessionState.BREAK
                ) {
                    moveToNextExercise()
                }
            }
    }

    // ---------------------------------------------------------
    // Next exercise
    // ---------------------------------------------------------

    private fun moveToNextExercise() {

        timerJob = null

        currentExerciseIndex++

        startCountdown()
    }

    // ---------------------------------------------------------
    // Pause
    // ---------------------------------------------------------

    fun pauseSession() {

        if (
            sessionState !=
            WorkoutSessionState.RUNNING
        ) {
            return
        }

        timerJob?.cancel()

        sessionState =
            WorkoutSessionState.PAUSED
    }

    // ---------------------------------------------------------
    // Resume
    // ---------------------------------------------------------

    fun resumeSession() {

        if (
            sessionState !=
            WorkoutSessionState.PAUSED
        ) {
            return
        }

        sessionState =
            WorkoutSessionState.RUNNING

        timerJob?.cancel()

        timerJob =
            viewModelScope.launch {
                runExerciseTimer()
            }
    }

    // ---------------------------------------------------------
    // Finish workout manually
    // ---------------------------------------------------------

    fun finishSession() {

        timerJob?.cancel()

        finishWorkout()
    }

    // ---------------------------------------------------------
    // Finish workout internally
    // ---------------------------------------------------------

    private fun finishWorkout() {

        timerJob?.cancel()

        timerJob = null

        remainingTime = 0

        displayText =
            totalTime.toHmsString()

        sessionState =
            WorkoutSessionState.FINISHED

        viewModelScope.launch {
            _sound.send(
                WorkoutSound.FINISH
            )
        }
    }

    // ---------------------------------------------------------
    // Cancel workout
    // ---------------------------------------------------------

    fun cancelSession() {

        timerJob?.cancel()

        timerJob = null

        currentExerciseIndex = 0

        remainingTime = 0

        totalTime = 0
        totalSessionTime = 0

        displayText = "0:00"

        sessionState =
            WorkoutSessionState.SETUP
    }

    // ---------------------------------------------------------
    // Reset
    // ---------------------------------------------------------

    fun reset() {

        timerJob?.cancel()

        timerJob = null

        currentExerciseIndex = 0

        remainingTime = 0

        totalTime = 0
        totalSessionTime = 0

        displayText = "0:00"

        sessionState =
            WorkoutSessionState.SETUP
    }

    // ---------------------------------------------------------
    // Factory
    // ---------------------------------------------------------

    companion object {

        fun factory(
            workoutRepository: WorkoutRepository,
            workoutExerciseRepository: WorkoutExerciseRepository,
            exerciseRepository: ExerciseRepository,
            userProfileRepository: UserProfileRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {

                    return WorkoutSessionViewModel(
                        workoutRepository,
                        workoutExerciseRepository,
                        exerciseRepository,
                        userProfileRepository
                    ) as T
                }
            }
    }
}