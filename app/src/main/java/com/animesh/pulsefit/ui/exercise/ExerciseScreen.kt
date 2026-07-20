package com.animesh.pulsefit.ui.exercise

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.exercise.components.EmptySection
import com.animesh.pulsefit.ui.exercise.components.ExerciseCard
import com.animesh.pulsefit.ui.exercise.components.SectionTitle
import com.animesh.pulsefit.viewmodel.ExerciseViewModel
import com.animesh.pulsefit.ui.components.CreateBottomSheet

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel
) {

    var showSearch by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showCreateSheet by rememberSaveable { mutableStateOf(false) }

    val exercises by viewModel.exercises.collectAsStateWithLifecycle()

    val filteredExercises = remember(exercises, searchQuery) {
        if (searchQuery.isBlank()) {
            exercises
        } else {
            exercises.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showCreateSheet = true
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24px),
                    contentDescription = "Add"
                )
            }
        }
    ) { _ ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                SectionTitle("Favorites")
                EmptySection("No favorite exercises")
            }

            item {

                SectionTitle(
                    title = "Exercises",
                    showSearch = true,
                    onSearchClick = {
                        showSearch = !showSearch

                        if (!showSearch) {
                            searchQuery = ""
                        }
                    }
                )

                if (showSearch) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp,vertical = 8.dp),
                        singleLine = true,
                        placeholder = {
                            Text("Search exercises...")
                        },
                        shape = RoundedCornerShape(50.dp)
                    )
                }
            }

            items(filteredExercises) { exercise ->

                ExerciseCard(
                    exercise = exercise,
                    onFavoriteClick = {
                        viewModel.toggleFavorite(exercise)
                    }
                )
            }

            item {
                SectionTitle(
                    title = "Workouts",
                    showSearch = true,
                    onSearchClick = {
                        // TODO: Workout search later
                    }
                )

                EmptySection("No workouts")
            }
        }
        if (showCreateSheet) {
            CreateBottomSheet(
                onDismiss = {
                    showCreateSheet = false
                },
                onCreateExercise = {
                    // TODO: Navigate to AddExerciseScreen
                },
                onCreateWorkout = {
                    // TODO: Navigate to AddWorkoutScreen
                }
            )
        }
    }
}