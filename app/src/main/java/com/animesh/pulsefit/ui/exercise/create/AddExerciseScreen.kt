package com.animesh.pulsefit.ui.exercise.create

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.viewmodel.AddExerciseViewModel

@Composable
fun AddExerciseScreen(
    rootNavController: NavHostController,
    addExerciseViewModel: AddExerciseViewModel
) {
    ExerciseFormScreen(
        title = "Add Exercise",

        initialExercise = null,

        onBack = {
            rootNavController.popBackStack()
        },

        onSave = { exercise ->

            val success =
                addExerciseViewModel.createExercise(exercise)

            if (success) {
                rootNavController.popBackStack()
            }
            success
        }
    )
}