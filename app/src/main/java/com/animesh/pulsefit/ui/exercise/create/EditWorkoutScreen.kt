package com.animesh.pulsefit.ui.exercise.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.animesh.pulsefit.viewmodel.AddWorkoutViewModel

@Composable
fun EditWorkoutScreen(
    workoutId: Long,
    rootNavController: NavHostController,
    viewModel: AddWorkoutViewModel
) {

    LaunchedEffect(workoutId) {
        viewModel.loadWorkout(workoutId)
    }

    WorkoutFormScreen(
        title = "Edit Workout",
        rootNavController = rootNavController,
        viewModel = viewModel,
        saveButtonText = "Update Workout",
        onSave = {viewModel.updateWorkout()}
    )
}