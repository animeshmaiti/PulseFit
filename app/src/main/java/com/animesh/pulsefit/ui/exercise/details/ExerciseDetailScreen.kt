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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseRunningContent
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseSetupContent
import com.animesh.pulsefit.viewmodel.ExerciseDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    exerciseId: Long,
    rootNavController: NavHostController,
    viewModel:ExerciseDetailViewModel
) {
    var duration by rememberSaveable {
        mutableIntStateOf(30)
    }
    var sessionState by rememberSaveable {
        mutableStateOf(SessionState.SETUP)
    }
    LaunchedEffect(exerciseId) {
        viewModel.loadExercise(exerciseId)
    }
    val exercise = viewModel.exercise
    val isSetup = sessionState == SessionState.SETUP
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(exercise?.name ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        rootNavController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back_24px),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {

                    if (isSetup) {

                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(R.drawable.edit_24px),
                                contentDescription = "Edit"
                            )
                        }

                        IconButton(onClick = { }) {
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
                        onDurationChange = { duration = it }
                    )
                }

                SessionState.RUNNING -> {
                    ExerciseRunningContent(
                        // Later
                    )
                }

                SessionState.PAUSED -> {
                    ExerciseRunningContent(
                        // Later (paused UI)
                    )
                }

                SessionState.FINISHED -> {
                    // Later
                }
            }

            when (sessionState) {

                SessionState.SETUP -> {

                    Button(
                        onClick = {
                            sessionState = SessionState.RUNNING
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text("Start")
                    }
                }

                SessionState.RUNNING -> {

                    // Later:
                    // [ Pause ] [ Finish ]
                }

                SessionState.PAUSED -> {

                    // Later:
                    // [ Resume ] [ Finish ] [ Cancel ]
                }

                SessionState.FINISHED -> {
                    // Nothing for now
                }
            }
        }
    }
}