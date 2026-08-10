package com.animesh.pulsefit.ui.navigation

import com.animesh.pulsefit.R

sealed class MainScreen(
    val route: String,
    val title: String,
    val icon: Int?
) {

   data object Home : MainScreen("home", "Home", R.drawable.home_24px)

   data object Exercise : MainScreen("exercise", "Exercise", R.drawable.exercise_24px)

   data object Progress : MainScreen("progress", "Progress", R.drawable.monitoring_24px)

   data object Settings : MainScreen("settings", "Settings", null)
}