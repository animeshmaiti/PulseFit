package com.animesh.pulsefit.ui.exercise.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.animesh.pulsefit.ui.components.PulseFitBackTopBar
import com.animesh.pulsefit.ui.exercise.components.ExerciseCard
import com.animesh.pulsefit.viewmodel.AddWorkoutViewModel

@Composable
fun SelectExercisesScreen(
    rootNavController: NavHostController,
    viewModel: AddWorkoutViewModel
) {

    val exercises by viewModel.exercises.collectAsStateWithLifecycle()

    Scaffold(

        topBar = {
            PulseFitBackTopBar(
                title = "Select Exercises",
                onBack = {
                    rootNavController.popBackStack()
                }
            )
        },

        bottomBar = {

            Column(
                modifier = Modifier.navigationBarsPadding()
            ) {

                HorizontalDivider()

                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        onClick = {
                            rootNavController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            // TODO
                        },
                        modifier = Modifier.weight(1f),
                        enabled = false
                    ) {
                        Text("Add")
                    }
                }
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            items(
                exercises,
                key = { it.id }
            ) { exercise ->

                ExerciseCard(
                    exercise = exercise,
                    onClick = {
                        // TODO
                    }
                )
            }
        }
    }
}