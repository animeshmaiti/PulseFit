package com.animesh.pulsefit.ui.exercise.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.ui.components.PulseFitBackTopBar
import com.animesh.pulsefit.ui.exercise.components.EmptySection
import com.animesh.pulsefit.ui.exercise.components.SectionTitle
import com.animesh.pulsefit.ui.exercise.components.WorkoutExerciseCard
import com.animesh.pulsefit.ui.navigation.RootScreen
import com.animesh.pulsefit.viewmodel.AddWorkoutViewModel

@Composable
fun AddWorkoutScreen(
    rootNavController: NavHostController,
    viewModel: AddWorkoutViewModel
) {

    Scaffold(
        topBar = {
            PulseFitBackTopBar(
                title = "Add Workout",
                onBack = {
                    rootNavController.popBackStack()
                }
            )
        },

        bottomBar = {
            Surface(
                color = Color.Transparent,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Button(
                    onClick = {
                        // TODO Save Workout
                    },
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Save Workout")
                }
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            OutlinedTextField(
                value = viewModel.workoutName,
                onValueChange = viewModel::onWorkoutNameChanged,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Workout Name")
                },
                singleLine = true
            )

            OutlinedTextField(
                value = viewModel.description,
                onValueChange = viewModel::onDescriptionChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = {
                    Text("Description")
                }
            )

            Column {

                SectionTitle("Exercises")

                if (viewModel.selectedExercises.isEmpty()) {

                    EmptySection(
                        "No exercises added yet."
                    )

                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        viewModel.selectedExercises.forEach { workoutExercise ->

                            WorkoutExerciseCard(
                                workoutExercise = workoutExercise,
                                onEditDuration = {
                                    viewModel.updateDuration(
                                        workoutExercise.exercise.id,
                                        30 // Temporary static value
                                    )
                                },
                                onRemove = {
                                    viewModel.removeExercise(
                                        workoutExercise.exercise.id
                                    )
                                }
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = {
                        rootNavController.navigate(
                            RootScreen.SelectExercises.route
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("+ Add Exercise")
                }
            }

            Column {

                SectionTitle("Duration")

                Text(
                    text = "00:00:00",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}