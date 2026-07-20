package com.animesh.pulsefit.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.res.painterResource

@Composable
fun BottomBar(
    navController: NavController
) {

    val items = listOf(
        MainScreen.Home,
        MainScreen.Exercise,
        MainScreen.Progress
    )

    NavigationBar {

        val backStackEntry =
            navController.currentBackStackEntryAsState()

        val currentRoute =
            backStackEntry.value?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,

                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = {
                    Icon(
                        painter = painterResource(screen.icon!!),
                        contentDescription = screen.title
                    )
                },

                label = {
                    Text(screen.title)
                }
            )
        }
    }
}