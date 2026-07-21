package com.animesh.pulsefit.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animesh.pulsefit.ui.components.PulseFitTopBar
import com.animesh.pulsefit.ui.exercise.ExerciseScreen
import com.animesh.pulsefit.ui.home.HomeScreen
import com.animesh.pulsefit.ui.profile.ProfileScreen
import com.animesh.pulsefit.ui.progress.ProgressScreen
import com.animesh.pulsefit.ui.settings.SettingsScreen
import com.animesh.pulsefit.viewmodel.ExerciseViewModel

@Composable
fun MainGraph(
    rootNavController: NavHostController,
    exerciseViewModel: ExerciseViewModel
) {

    val mainNavController = rememberNavController()

    Scaffold(

        topBar = {
            PulseFitTopBar(
                title = "PulseFit",
                navController = mainNavController
            )
        },

        bottomBar = {
            BottomBar(mainNavController)
        }

    ) { padding ->

        NavHost(
            navController = mainNavController,
            startDestination = MainScreen.Exercise.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(MainScreen.Home.route) {
                HomeScreen()
            }

            composable(MainScreen.Exercise.route) {

                ExerciseScreen(
                    viewModel = exerciseViewModel,
                    rootNavController = rootNavController
                )
            }

            composable(MainScreen.Progress.route) {
                ProgressScreen()
            }

            composable(MainScreen.Profile.route) {
                ProfileScreen()
            }

            composable(MainScreen.Settings.route) {
                SettingsScreen()
            }

        }
    }
}