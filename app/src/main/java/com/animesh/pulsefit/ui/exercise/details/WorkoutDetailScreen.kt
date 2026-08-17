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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.ui.components.PulseFitBackTopBar
import com.animesh.pulsefit.ui.exercise.details.components.BreakTimelineItem
import com.animesh.pulsefit.ui.exercise.details.components.ExerciseTimelineItem
import com.animesh.pulsefit.viewmodel.WorkoutDetailViewModel
import com.animesh.pulsefit.viewmodel.utils.formatDuration

@Composable
fun WorkoutDetailScreen(
    workoutId: Long,
    rootNavController: NavHostController,
    viewModel: WorkoutDetailViewModel
) {

    LaunchedEffect(workoutId) {
        viewModel.loadWorkout(workoutId)
    }

    val workout = viewModel.workout ?: return

    Scaffold(
        topBar = {
            PulseFitBackTopBar(
                title = workout.name,
                onBack = {
                    rootNavController.popBackStack()
                }
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

            // Edit

            OutlinedButton(
                onClick = {
                    // Edit workout later
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Edit")
            }
        }
    }
}