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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.audio.AudioPlayer
import com.animesh.pulsefit.ui.exercise.details.components.BreakTimelineItem
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseTimelineItem
import com.animesh.pulsefit.ui.navigation.RootScreen
import com.animesh.pulsefit.viewmodel.WorkoutDetailViewModel
import com.animesh.pulsefit.viewmodel.utils.formatDuration
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    workoutId: Long,
    rootNavController: NavHostController,
    viewModel: WorkoutDetailViewModel
) {

    LaunchedEffect(workoutId) {
        viewModel.loadWorkout(workoutId)
    }
    val scope = rememberCoroutineScope()
    val workout = viewModel.workout ?: return
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val sessionState = viewModel.sessionState

    val isSetup = sessionState == SessionState.SETUP
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
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(workout.name ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        when (viewModel.sessionState) {

                            SessionState.SETUP -> {
                                rootNavController.popBackStack()
                            }

                            else -> {
                                AudioPlayer.release()
//                                viewModel.reset()
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

                    if (isSetup == true) {

                        IconButton(
                            onClick = {
                                rootNavController.navigate(
                                    RootScreen.EditWorkout.createRoute(workoutId)
                                )
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.edit_24px),
                                contentDescription = "Edit"
                            )
                        }

                        IconButton(
                            onClick = {
                                showDeleteDialog = true
                            }
                        ) {
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
                .padding(16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Workout description

            if (workout.description.isNotBlank()) {

                Text(
                    text = workout.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Workout information

            Text(
                text = "${viewModel.exerciseCount} exercises, " +
                        "About ${formatDuration(viewModel.totalDuration)}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Exercise timeline

            viewModel.exercises.forEachIndexed { index, workoutExercise ->

                ExerciseTimelineItem(
                    title=workoutExercise.exerciseName,
                    duration = workoutExercise.duration
                )

                // Break after exercise
                if (index < viewModel.exercises.lastIndex) {

                    BreakTimelineItem(
                        breakDuration = workoutExercise.breakDuration
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Description / information

            Text(
                text = "When you start this workout, the exercises " +
                        "will be performed in the order shown above.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Start

            Button(
                onClick = {
                    // Workout session later
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Start")
            }
        }
    }
}