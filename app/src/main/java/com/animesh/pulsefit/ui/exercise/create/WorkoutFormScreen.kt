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
import com.animesh.pulsefit.viewmodel.utils.toHmsString

@Composable
fun WorkoutFormScreen(
    title: String,
    saveButtonText: String,
    rootNavController: NavHostController,
    viewModel: AddWorkoutViewModel,
    onSave: () -> Unit
) {
    Scaffold(

        topBar = {
            PulseFitBackTopBar(
                title = title,
                onBack = {
                    viewModel.reset()
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
                        onSave()
                        rootNavController.popBackStack()
                    },
                    enabled =
                    viewModel.selectedExercises.isNotEmpty() &&
                            viewModel.workoutName.isNotBlank(),

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(saveButtonText)
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

            verticalArrangement =
            Arrangement.spacedBy(20.dp)
        ) {

            // -------------------------------------------------
            // Workout name
            // -------------------------------------------------

            OutlinedTextField(
                value = viewModel.workoutName,
                onValueChange = viewModel::onWorkoutNameChanged,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Workout Name")
                },
                singleLine = true
            )


            // -------------------------------------------------
            // Description
            // -------------------------------------------------

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


            // -------------------------------------------------
            // Exercises
            // -------------------------------------------------

            Column {

                SectionTitle("Exercises")

                if (viewModel.selectedExercises.isEmpty()) {

                    EmptySection(
                        "No exercises added yet."
                    )

                } else {

                    Column(
                        verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                    ) {

                        viewModel.selectedExercises.forEach {
                                workoutExercise ->

                            WorkoutExerciseCard(

                                workoutExercise =
                                workoutExercise,

                                // Exercise duration
                                onEditDuration = { seconds ->

                                    viewModel.updateDuration(
                                        workoutExercise.exercise.id,
                                        seconds
                                    )
                                },

                                // Remove exercise
                                onRemove = {

                                    viewModel.removeExercise(
                                        workoutExercise.exercise.id
                                    )
                                },

                                // Break type
                                onBreakTypeChange = { breakType ->

                                    viewModel.updateBreakType(
                                        workoutExercise.exercise.id,
                                        breakType
                                    )
                                },

                                // Break duration
                                onEditBreakDuration = { seconds ->

                                    viewModel.updateBreakDuration(
                                        workoutExercise.exercise.id,
                                        seconds
                                    )
                                }
                            )
                        }
                    }
                }


                // Add exercise button

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


            // -------------------------------------------------
            // Total duration
            // -------------------------------------------------

            Column {

                SectionTitle("Duration")

                Text(
                    text =
                    viewModel
                        .getTotalDuration()
                        .toHmsString(),

                    style =
                    MaterialTheme.typography.headlineMedium,

                    modifier =
                    Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}