package com.animesh.pulsefit.ui.exercise.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseFinishedContent
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseRunningContent
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseSetupContent
import com.animesh.pulsefit.viewmodel.ExerciseDetailViewModel
import com.animesh.pulsefit.viewmodel.utils.toHmsString
import androidx.compose.ui.platform.LocalContext
import com.animesh.pulsefit.ui.audio.AudioPlayer
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseCaloriesContent
import com.animesh.pulsefit.ui.navigation.RootScreen
import com.animesh.pulsefit.viewmodel.event.WorkoutSound

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    exerciseId: Long,
    rootNavController: NavHostController,
    viewModel: ExerciseDetailViewModel
) {

    LaunchedEffect(exerciseId) {
        viewModel.loadExercise(exerciseId)
    }
    LaunchedEffect(Unit) {
        viewModel.deleted.collect {
            rootNavController.popBackStack()
        }
    }
    val exercise = viewModel.exercise
    val duration = viewModel.selectedDuration
    val sessionState = viewModel.sessionState

    val isSetup = sessionState == SessionState.SETUP

    val context = LocalContext.current

    LaunchedEffect(Unit) {

        viewModel.sound.collect { sound ->

            when (sound) {

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
    DisposableEffect(Unit) {
        onDispose {
            AudioPlayer.release()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(exercise?.name ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        when (viewModel.sessionState) {

                            SessionState.SETUP -> {
                                rootNavController.popBackStack()
                            }

                            else -> {
                                AudioPlayer.release()
                                viewModel.reset()
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back_24px),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {

                    if (isSetup && exercise?.isBuiltIn == false) {

                        IconButton(
                            onClick = {
                                rootNavController.navigate(
                                    RootScreen.EditExercise.createRoute(exerciseId)
                                )
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.edit_24px),
                                contentDescription = "Edit"
                            )
                        }

                        IconButton(onClick = {
                            viewModel.deleteExercise()
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.delete_24px),
                                contentDescription = "Delete"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            when (sessionState) {

                SessionState.SETUP -> {
                    ExerciseSetupContent(
                        duration = duration,
                        onDurationChange = viewModel::updateDuration
                    )
                }

                SessionState.COUNTDOWN,
                SessionState.RUNNING,
                SessionState.PAUSED -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ExerciseRunningContent(
                            displayText = viewModel.displayText,
                            sessionState = sessionState
                        )
                        ExerciseCaloriesContent(
                            calories = viewModel.caloriesBurned,
                            sessionState = sessionState,
                            hasCaloriesData = viewModel.hasCaloriesData
                        )
                    }
                }

                SessionState.FINISHED -> {
                    ExerciseFinishedContent(
                        exerciseName = exercise?.name ?: "",
                        duration = viewModel.totalTime.toHmsString(),
                        calories = viewModel.caloriesBurned,
                    )
                }
            }


            when (sessionState) {

                SessionState.SETUP -> {

                    Button(
                        onClick = viewModel::startSession,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("Start")
                    }
                }

                SessionState.COUNTDOWN -> {
                    // No buttons during countdown
                }

                SessionState.RUNNING -> {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = viewModel::pauseSession,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Pause")
                        }

                        Button(
                            onClick = viewModel::finishSession,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Finish")
                        }
                    }
                }

                SessionState.PAUSED -> {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = viewModel::resumeSession,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Resume")
                        }

                        Button(
                            onClick = viewModel::finishSession,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Finish")
                        }

                        Button(
                            onClick = viewModel::cancelSession,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                        ) {
                            Text("Cancel")
                        }
                    }
                }

                SessionState.FINISHED -> {
                    Button(
                        onClick = {
                            viewModel.reset()
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