package com.animesh.pulsefit.ui.navigation

import androidx.annotation.DrawableRes
import com.animesh.pulsefit.R

sealed class Screen(
    val route: String,
    val title: String,
    @param:DrawableRes val icon: Int? = null
) {
    object Home : Screen(
        route = "home",
        title = "Home",
        icon = R.drawable.home_24px
    )

    object Exercise : Screen(
        route = "exercise",
        title = "Exercise",
        icon = R.drawable.exercise_24px
    )

    object Progress : Screen(
        route = "progress",
        title = "Progress",
        icon = R.drawable.monitoring_24px
    )

    object Profile : Screen(
        route = "profile",
        title = "Profile"
    )

    object Settings : Screen(
        route = "settings",
        title = "Settings"
    )
}