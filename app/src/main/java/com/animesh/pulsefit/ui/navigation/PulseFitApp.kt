package com.animesh.pulsefit.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.animesh.pulsefit.PulseFitApplication
import com.animesh.pulsefit.ui.components.PulseFitTopBar
import com.animesh.pulsefit.ui.exercise.AddExerciseScreen
import com.animesh.pulsefit.ui.exercise.AddWorkoutScreen
import com.animesh.pulsefit.ui.exercise.ExerciseScreen
import com.animesh.pulsefit.ui.home.HomeScreen
import com.animesh.pulsefit.ui.profile.ProfileScreen
import com.animesh.pulsefit.ui.progress.ProgressScreen
import com.animesh.pulsefit.ui.settings.SettingsScreen
import com.animesh.pulsefit.viewmodel.ExerciseViewModel

@Composable
fun PulseFitApp() {

    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val mainScreens = setOf(
        Screen.Home.route,
        Screen.Exercise.route,
        Screen.Progress.route,
        Screen.Profile.route
    )

    val showMainBars = currentRoute in mainScreens

    Scaffold(
        topBar = {
            if (showMainBars) {
                PulseFitTopBar(
                    title = "PulseFit",
                    navController = navController
                )
            }
        },
        bottomBar = {
            if (showMainBars) {
                BottomBar(navController)
            }
        }
    ) { padding ->
        val navModifier =
        if (showMainBars) {
            Modifier.padding(padding)
        } else {
            Modifier
        }
        NavHost(
            navController = navController,
            startDestination = Screen.Exercise.route,
            modifier = navModifier
        ) {

            composable(Screen.Home.route) {
                HomeScreen()
            }

            composable(Screen.Exercise.route) {

                val app =
                    LocalContext.current.applicationContext as PulseFitApplication

                val viewModel: ExerciseViewModel = viewModel(
                    factory = ExerciseViewModel.factory(
                        app.exerciseRepository
                    )
                )

                ExerciseScreen(
                    viewModel = viewModel,
                    onNavigateToAddExercise = {
                        navController.navigate(Screen.AddExercise.route)
                    },
                    onNavigateToAddWorkout = {
                        navController.navigate(Screen.AddWorkout.route)
                    }
                )
            }

            composable(Screen.Progress.route) {
                ProgressScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(Screen.Settings.route) {
                SettingsScreen()
            }

            composable(Screen.AddExercise.route) {
                AddExerciseScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.AddWorkout.route) {
                AddWorkoutScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}