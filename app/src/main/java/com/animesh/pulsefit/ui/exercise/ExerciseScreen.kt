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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.components.bottomsheet.CreateBottomSheet
import com.animesh.pulsefit.ui.exercise.components.EmptySection
import com.animesh.pulsefit.ui.exercise.components.ExerciseCard
import com.animesh.pulsefit.ui.exercise.components.SectionTitle
import com.animesh.pulsefit.ui.exercise.components.WorkoutCard
import com.animesh.pulsefit.ui.navigation.RootScreen
import com.animesh.pulsefit.viewmodel.ExerciseViewModel

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel,
    rootNavController: NavHostController
) {
    var showSearch by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showCreateSheet by rememberSaveable { mutableStateOf(false) }

    val exercises by viewModel.exercises.collectAsStateWithLifecycle()
    val workouts by viewModel.workouts.collectAsStateWithLifecycle()

    val filteredExercises = remember(exercises, searchQuery) {
        if (searchQuery.isBlank()) {
            exercises
        } else {
            exercises.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    val favoriteExercises = remember(exercises) {
        exercises.filter { it.isFavorite }
    }

    val favoriteWorkouts = remember(workouts) {
        workouts.filter { it.isFavorite }
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
            modifier = Modifier.fillMaxSize()
        ) {

            item {
                SectionTitle("Favorites")
            }

            if (favoriteExercises.isEmpty() && favoriteWorkouts.isEmpty()) {

                item {
                    EmptySection("No favorite exercises or workouts")
                }

            } else {

                items(favoriteExercises) { exercise ->

                    ExerciseCard(
                        exercise = exercise,
                        onFavoriteClick = {
                            viewModel.toggleFavoriteExercise(exercise)
                        }
                    )
                }

                items(favoriteWorkouts) { workout ->

                    WorkoutCard(
                        workout = workout,
                        onFavoriteClick = {
                            viewModel.toggleFavoriteWorkout(workout)
                        }
                    )
                }
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
                            .padding(horizontal = 8.dp, vertical = 8.dp),
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
                        viewModel.toggleFavoriteExercise(exercise)
                    }
                )
            }

            item {
                SectionTitle(title = "Workouts")
            }

            if (workouts.isEmpty()) {
                item {
                    EmptySection("No workouts")
                }
            } else {
                items(workouts) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onFavoriteClick = {
                            viewModel.toggleFavoriteWorkout(workout)
                        },
                        onClick = {
                            // TODO
                        }
                    )
                }
            }
        }

        if (showCreateSheet) {
            CreateBottomSheet(

                onDismiss = {
                    showCreateSheet = false
                },

                onCreateExercise = {
                    showCreateSheet = false
                    rootNavController.navigate(
                        RootScreen.AddExercise.route
                    )
                },

                onCreateWorkout = {
                    showCreateSheet = false
                    rootNavController.navigate(
                        RootScreen.AddWorkout.route
                    )
                }
            )
        }
    }
}