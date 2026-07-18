package com.animesh.pulsefit.ui.exercise

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.exercise.components.EmptySection
import com.animesh.pulsefit.ui.exercise.components.SectionTitle
import com.animesh.pulsefit.viewmodel.ExerciseViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.lazy.items
import com.animesh.pulsefit.ui.exercise.components.ExerciseCard

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel
) {
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24px),
                    contentDescription = "Add"
                )
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            item {
                SectionTitle("Favorites")
                EmptySection("No favorite exercises")
            }

            item {
                SectionTitle("Exercises")
            }

            items(exercises) { exercise ->

                ExerciseCard(
                    exercise = exercise,
                    onFavoriteClick = {
                        viewModel.toggleFavorite(exercise)
                    }
                )
            }

            item {
                SectionTitle("Workouts")
                EmptySection("No workouts")
            }

        }

    }

}