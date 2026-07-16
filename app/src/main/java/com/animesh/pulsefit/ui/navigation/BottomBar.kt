package com.animesh.pulsefit.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.animesh.pulsefit.R
import androidx.compose.ui.res.painterResource

@Composable
fun BottomBar(
    navController: NavController
) {

    val items = listOf(
        Screen.Home,
        Screen.Exercise,
        Screen.Progress,
        Screen.Profile
    )

    NavigationBar {

        val backStackEntry =
            navController.currentBackStackEntryAsState()

        val currentRoute =
            backStackEntry.value?.destination?.route

        items.forEach { screen ->

            val icon = when (screen) {
                Screen.Home -> R.drawable.home_24px
                Screen.Exercise -> R.drawable.exercise_24px
                Screen.Progress -> R.drawable.monitoring_24px
                Screen.Profile -> R.drawable.account_circle_24px
            }

            NavigationBarItem(
                selected = currentRoute == screen.route,

                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },

                icon = {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = screen.title,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },

                label = {
                    Text(screen.title)
                }
            )
        }
    }
}