package com.animesh.pulsefit.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animesh.pulsefit.ui.components.PulseFitTopBar
import com.animesh.pulsefit.ui.exercise.ExerciseScreen
import com.animesh.pulsefit.ui.home.HomeScreen
import com.animesh.pulsefit.ui.profile.ProfileScreen
import com.animesh.pulsefit.ui.progress.ProgressScreen
import com.animesh.pulsefit.ui.settings.SettingsScreen

@Composable
fun PulseFitApp() {

    val navController = rememberNavController()

    androidx.compose.material3.Scaffold(
        topBar = {
            PulseFitTopBar(
                title = "PulseFit",
                navController = navController
            )
        },
        bottomBar = {
            BottomBar(navController)
        }

    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Home.route) {
                HomeScreen()
            }

            composable(Screen.Exercise.route) {
                ExerciseScreen()
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

        }

    }

}