package com.animesh.pulsefit.ui.navigation

sealed class Screen(
    val route: String,
    val title: String
) {
    object Home : Screen("home", "Home")
    object Exercise : Screen("exercise", "Exercise")
    object Progress : Screen("progress", "Progress")
    object Profile : Screen("profile", "Profile")
}