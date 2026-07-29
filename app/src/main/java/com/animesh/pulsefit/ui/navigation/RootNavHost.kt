package com.animesh.pulsefit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animesh.pulsefit.PulseFitApplication
import com.animesh.pulsefit.ui.exercise.create.AddExerciseScreen
import com.animesh.pulsefit.ui.exercise.create.AddWorkoutScreen
import com.animesh.pulsefit.ui.exercise.details.ExerciseDetailScreen
import com.animesh.pulsefit.ui.exercise.picker.SelectExercisesScreen
import com.animesh.pulsefit.viewmodel.AddExerciseViewModel
import com.animesh.pulsefit.viewmodel.AddWorkoutViewModel
import com.animesh.pulsefit.viewmodel.ExerciseDetailViewModel
import com.animesh.pulsefit.viewmodel.ExerciseViewModel

@Composable
fun RootNavHost() {

    val rootNavController = rememberNavController()
    val app =
        LocalContext.current.applicationContext as PulseFitApplication

    val exerciseViewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModel.factory(
            app.exerciseRepository,
            app.workoutRepository
        )
    )
    val addExerciseViewModel: AddExerciseViewModel = viewModel(
        factory = AddExerciseViewModel.factory(
            app.exerciseRepository
        )
    )
    val addWorkoutViewModel: AddWorkoutViewModel = viewModel(
        factory = AddWorkoutViewModel.factory(
            app.exerciseRepository,
            app.workoutBuilderRepository
        )
    )
    val exerciseDetailViewModel: ExerciseDetailViewModel = viewModel(
        factory = ExerciseDetailViewModel.factory(
            app.exerciseRepository,
        )
    )


    NavHost(
        navController = rootNavController,
        startDestination = RootScreen.Main.route
    ) {

        composable(RootScreen.Main.route) {
            MainGraph(
                rootNavController = rootNavController,
                exerciseViewModel = exerciseViewModel,
                addExerciseViewModel = addExerciseViewModel,
                addWorkoutViewModel = addWorkoutViewModel
            )
        }

        composable(RootScreen.AddExercise.route) {
            AddExerciseScreen(
                rootNavController = rootNavController,
                addExerciseViewModel = addExerciseViewModel
            )
        }

        composable(RootScreen.AddWorkout.route) {
            AddWorkoutScreen(
                rootNavController = rootNavController,
                viewModel = addWorkoutViewModel
            )
        }

        composable(RootScreen.SelectExercises.route) {
            SelectExercisesScreen(
                rootNavController = rootNavController,
                viewModel = addWorkoutViewModel
            )
        }

//        composable(
//            RootScreen.EditExercise.route
//        ) {
//            EditExerciseScreen(rootNavController)
//        }
//
        composable(
            route = RootScreen.ExerciseDetail.route
        ) { backStackEntry ->

            val exerciseId =
                backStackEntry.arguments
                    ?.getString("exerciseId")
                    ?.toLongOrNull() ?: return@composable

            ExerciseDetailScreen(
                exerciseId = exerciseId,
                rootNavController = rootNavController,
                viewModel = exerciseDetailViewModel
            )
        }
//
//        composable(
//            RootScreen.EditWorkout.route
//        ) {
//            EditWorkoutScreen(rootNavController)
//        }
//
//        composable(
//            RootScreen.WorkoutSession.route
//        ) {
//            WorkoutSessionScreen(rootNavController)
//        }
    }
}