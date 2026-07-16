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

@Composable
fun ExerciseScreen() {
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
                EmptySection("No exercises")
            }

            item {
                SectionTitle("Workouts")
                EmptySection("No workouts")
            }

        }

    }

}