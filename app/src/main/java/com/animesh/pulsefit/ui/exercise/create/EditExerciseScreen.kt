package com.animesh.pulsefit.ui.exercise.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.animesh.pulsefit.ui.exercise.create.ExerciseFormScreen
import com.animesh.pulsefit.viewmodel.EditExerciseViewModel

@Composable
fun EditExerciseScreen(
    exerciseId: Long,
    rootNavController: NavHostController,
    viewModel: EditExerciseViewModel
) {

    LaunchedEffect(exerciseId) {
        viewModel.loadExercise(exerciseId)
    }

    LaunchedEffect(Unit) {
        viewModel.updated.collect {
            rootNavController.popBackStack()
        }
    }

    val exercise = viewModel.exercise ?: return

    ExerciseFormScreen(
        title = "Edit Exercise",

        initialExercise = exercise,

        onBack = {
            rootNavController.popBackStack()
        },

        onSave = { updatedExercise ->
            viewModel.updateExercise(updatedExercise)
        }
    )
}