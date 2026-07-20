package com.animesh.pulsefit.ui.navigation

import androidx.annotation.DrawableRes
import com.animesh.pulsefit.R

sealed class Screen(
    val route: String,
    val title: String,
    @param:DrawableRes val icon: Int? = null
) {
    data object Home : Screen(
        route = "home",
        title = "Home",
        icon = R.drawable.home_24px
    )

    data object Exercise : Screen(
        route = "exercise",
        title = "Exercise",
        icon = R.drawable.exercise_24px
    )

    data object Progress : Screen(
        route = "progress",
        title = "Progress",
        icon = R.drawable.monitoring_24px
    )

    data object Profile : Screen(
        route = "profile",
        title = "Profile"
    )

    data object Settings : Screen(
        route = "settings",
        title = "Settings"
    )
    data object AddExercise : Screen(
        route = "add_exercise",
        title = "Add Exercise"
    )

    data object AddWorkout : Screen(
        route = "add_workout",
        title = "Add Workout"
    )
}