package com.animesh.pulsefit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animesh.pulsefit.ui.exercise.AddExerciseScreen
import com.animesh.pulsefit.ui.exercise.AddWorkoutScreen

@Composable
fun RootNavHost() {

    val rootNavController = rememberNavController()

    NavHost(
        navController = rootNavController,
        startDestination = RootScreen.Main.route
    ) {

        composable(RootScreen.Main.route) {
            MainGraph(rootNavController)
        }

        composable(RootScreen.AddExercise.route) {
            AddExerciseScreen(rootNavController)
        }

        composable(RootScreen.AddWorkout.route) {
            AddWorkoutScreen(rootNavController)
        }

//        composable(
//            RootScreen.EditExercise.route
//        ) {
//            EditExerciseScreen(rootNavController)
//        }
//
//        composable(
//            RootScreen.ExerciseDetail.route
//        ) {
//            ExerciseDetailScreen(rootNavController)
//        }
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