package com.animesh.pulsefit.ui.exercise.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.audio.AudioPlayer
import com.animesh.pulsefit.ui.exercise.details.components.BreakTimelineItem
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseCaloriesContent
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseFinishedContent
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseRunningContent
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseTimelineItem
import com.animesh.pulsefit.ui.navigation.RootScreen
import com.animesh.pulsefit.viewmodel.WorkoutDetailViewModel
import com.animesh.pulsefit.viewmodel.WorkoutSessionViewModel
import com.animesh.pulsefit.viewmodel.event.WorkoutSound
import com.animesh.pulsefit.viewmodel.utils.formatDuration
import com.animesh.pulsefit.viewmodel.utils.toHmsString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    workoutId: Long,
    rootNavController: NavHostController,
    viewModel: WorkoutDetailViewModel,
    sessionViewModel: WorkoutSessionViewModel
) {

    // ---------------------------------------------------------
    // Load workout
    // ---------------------------------------------------------

    LaunchedEffect(workoutId) {
        viewModel.loadWorkout(workoutId)
        sessionViewModel.loadWorkout(workoutId)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val workout = viewModel.workout ?: return

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    val sessionState = sessionViewModel.sessionState

    val isSetup =
        sessionState == WorkoutSessionState.SETUP


    // ---------------------------------------------------------
    // Sound
    // ---------------------------------------------------------

    LaunchedEffect(Unit) {

        sessionViewModel.sound.collect { sound ->

            when (sound) {
                WorkoutSound.EXERCISE_COMPLETE ->
                    AudioPlayer.play(
                        context,
                        R.raw.exercise_complete
                    )

                WorkoutSound.COUNTDOWN ->
                    AudioPlayer.play(
                        context,
                        R.raw.beep_sound
                    )

                WorkoutSound.GO ->
                    AudioPlayer.play(
                        context,
                        R.raw.beep_sound_end
                    )

                WorkoutSound.FINISH ->
                    AudioPlayer.play(
                        context,
                        R.raw.finishsound
                    )
            }
        }
    }


    // ---------------------------------------------------------
    // Release audio when leaving screen
    // ---------------------------------------------------------

    DisposableEffect(Unit) {
        onDispose {
            AudioPlayer.release()
        }
    }

    // ---------------------------------------------------------
    // Delete dialog
    // ---------------------------------------------------------

    if (showDeleteDialog) {

        AlertDialog(

            onDismissRequest = {
                showDeleteDialog = false
            },

            title = {
                Text("Delete workout?")
            },

            text = {
                Text(
                    "This will permanently delete this workout " +
                            "and its exercises."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        scope.launch {
                            viewModel.deleteWorkout()
                            rootNavController.popBackStack()
                        }
                    }
                ) {
                    Text("Delete")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


    // ---------------------------------------------------------
    // UI
    // ---------------------------------------------------------

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(workout.name)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            when (sessionViewModel.sessionState) {

                                WorkoutSessionState.SETUP -> {
                                    rootNavController.popBackStack()
                                }

                                WorkoutSessionState.FINISHED -> {
                                    sessionViewModel.reset()
                                }

                                else -> {
                                    AudioPlayer.release()
                                    sessionViewModel.cancelSession()
                                }
                            }
                        }
                    ) {

                        Icon(
                            painter = painterResource(
                                R.drawable.arrow_back_24px
                            ),
                            contentDescription = "Back"
                        )
                    }
                },

                actions = {

                    // Only show edit/delete before workout starts
                    if (isSetup) {

                        IconButton(
                            onClick = {

                                rootNavController.navigate(
                                    RootScreen.EditWorkout.createRoute(
                                        workoutId
                                    )
                                )
                            }
                        ) {

                            Icon(
                                painter = painterResource(
                                    R.drawable.edit_24px
                                ),
                                contentDescription = "Edit"
                            )
                        }


                        IconButton(
                            onClick = {
                                showDeleteDialog = true
                            }
                        ) {

                            Icon(
                                painter = painterResource(
                                    R.drawable.delete_24px
                                ),
                                contentDescription = "Delete"
                            )
                        }
                    }
                },

                colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { padding ->


        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            // -------------------------------------------------
            // SETUP
            // -------------------------------------------------

            if (sessionState == WorkoutSessionState.SETUP) {

                // Workout description
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (workout.description.isNotBlank()) {

                        Text(
                            text = workout.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }


                    // Workout information

                    Text(
                        text =
                        "${viewModel.exerciseCount} exercises, " +
                                "About ${formatDuration(viewModel.totalDuration)}",

                        style =
                        MaterialTheme.typography.titleMedium,

                        color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Exercise timeline

                    viewModel.exercises.forEachIndexed { index,
                                                         workoutExercise ->

                        ExerciseTimelineItem(
                            title = workoutExercise.exerciseName,
                            duration = workoutExercise.duration
                        )

                        // Break after exercise

                        if (index < viewModel.exercises.lastIndex) {

                            BreakTimelineItem(
                                breakDuration =
                                workoutExercise.breakDuration
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                        "When you start this workout, the exercises " +
                                "will be performed in the order shown above.",

                        style =
                        MaterialTheme.typography.bodyMedium,

                        color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }


            // -------------------------------------------------
            // COUNTDOWN / RUNNING / BREAK / PAUSED
            // -------------------------------------------------

                if (
                    sessionState == WorkoutSessionState.COUNTDOWN ||
                    sessionState == WorkoutSessionState.RUNNING ||
                    sessionState == WorkoutSessionState.BREAK ||
                    sessionState == WorkoutSessionState.PAUSED
                ) {

                    val currentExercise =
                        sessionViewModel.currentExercise


                    // Current exercise name
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = currentExercise?.exercise?.name ?: "",

                            style =
                            MaterialTheme.typography.headlineSmall
                        )


                        // Exercise number

                        Text(
                            text =
                            "Exercise ${sessionViewModel.exerciseNumber} " +
                                    "of ${sessionViewModel.exerciseCount}",

                            style =
                            MaterialTheme.typography.bodyMedium,

                            color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )


                        // Break label

                        if (sessionState == WorkoutSessionState.BREAK) {

                            Text(
                                text = "Break",

                                style =
                                MaterialTheme.typography.titleLarge,

                                color =
                                MaterialTheme.colorScheme.primary
                            )
                        }


                        // Timer

                        ExerciseRunningContent(
                            displayText =
                            sessionViewModel.displayText,

                            sessionState =
                            when (sessionState) {

                                WorkoutSessionState.COUNTDOWN ->
                                    SessionState.COUNTDOWN

                                WorkoutSessionState.RUNNING ->
                                    SessionState.RUNNING

                                WorkoutSessionState.PAUSED ->
                                    SessionState.PAUSED

                                // BREAK uses the running appearance
                                WorkoutSessionState.BREAK ->
                                    SessionState.RUNNING

                                else ->
                                    SessionState.RUNNING
                            }
                        )


                        // Calories

                        ExerciseCaloriesContent(
                            calories =
                            sessionViewModel.caloriesBurned,

                            sessionState =
                            when (sessionState) {

                                WorkoutSessionState.COUNTDOWN ->
                                    SessionState.COUNTDOWN

                                WorkoutSessionState.RUNNING ->
                                    SessionState.RUNNING

                                WorkoutSessionState.PAUSED ->
                                    SessionState.PAUSED

                                WorkoutSessionState.BREAK ->
                                    SessionState.RUNNING

                                else ->
                                    SessionState.RUNNING
                            },

                            hasCaloriesData =
                            sessionViewModel.hasCaloriesData
                        )
                    }
                }


            // -------------------------------------------------
            // FINISHED
            // -------------------------------------------------

            if (sessionState == WorkoutSessionState.FINISHED) {

                ExerciseFinishedContent(
                    exerciseName =
                    sessionViewModel.workoutName,

                    duration =
                    sessionViewModel.totalTime.toHmsString(),

                    calories =
                    sessionViewModel.caloriesBurned
                )
            }


            // -------------------------------------------------
            // Bottom buttons
            // -------------------------------------------------


            when (sessionState) {
                // ---------------------------------------------
                // SETUP
                // ---------------------------------------------

                WorkoutSessionState.SETUP -> {

                    Button(
                        onClick =
                        sessionViewModel::startSession,

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {

                        Text("Start")
                    }
                }


                // ---------------------------------------------
                // COUNTDOWN
                // ---------------------------------------------

                WorkoutSessionState.COUNTDOWN -> {

                    // No buttons during countdown
                }


                // ---------------------------------------------
                // RUNNING
                // ---------------------------------------------

                WorkoutSessionState.RUNNING -> {

                    Column(
                        verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick =
                            sessionViewModel::pauseSession,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Pause")
                        }


                        OutlinedButton(
                            onClick =
                            sessionViewModel::finishSession,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Finish")
                        }
                    }
                }


                // ---------------------------------------------
                // BREAK
                // ---------------------------------------------

                WorkoutSessionState.BREAK -> {

                    // No buttons during break.
                    // The next exercise starts automatically.
                }


                // ---------------------------------------------
                // PAUSED
                // ---------------------------------------------

                WorkoutSessionState.PAUSED -> {

                    Column(
                        verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick =
                            sessionViewModel::resumeSession,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {

                            Text("Resume")
                        }

                        OutlinedButton(
                            onClick =
                            sessionViewModel::finishSession,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Finish")
                        }


                        OutlinedButton(
                            onClick =
                            sessionViewModel::cancelSession,

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Cancel")
                        }
                    }
                }


                // ---------------------------------------------
                // FINISHED
                // ---------------------------------------------

                WorkoutSessionState.FINISHED -> {

                    Button(
                        onClick = {
                            sessionViewModel.reset()
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}