package com.animesh.pulsefit.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.navigation.MainScreen
import com.animesh.pulsefit.ui.navigation.RootScreen
import com.animesh.pulsefit.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    rootNavController: NavHostController,
    mainNavController: NavHostController
) {

    val homeStats by homeViewModel.homeStats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            HomeStatCard(
                title = "Streak",
                value = "${homeStats?.streak ?: 0} days",
                icon = R.drawable.burn,
                modifier = Modifier.weight(1f),
                onClick = {
                    mainNavController.navigate(MainScreen.Progress.route)
                }
            )

            HomeStatCard(
                title = "Last Exercise",
                value = homeStats?.lastExerciseName ?: "None",
                icon = R.drawable.lastex,
                modifier = Modifier.weight(1f),
                onClick = {
                    homeStats?.lastExerciseId?.let { id ->
                        rootNavController.navigate(RootScreen.ExerciseDetail.createRoute(id))
                    }
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            HomeStatCard(
                title = "Last Workout",
                value = homeStats?.lastWorkoutName ?: "None",
                icon = R.drawable.workout,
                modifier = Modifier.weight(1f),
                onClick = {
                    homeStats?.lastWorkoutId?.let { id ->
                        rootNavController.navigate(RootScreen.WorkoutDetail.createRoute(id))
                    }
                }
            )

            HomeStatCard(
                title = "Calories Today",
                value = "${homeStats?.caloriesBurned?.toInt() ?: 0} kcal",
                icon = R.drawable.kcal,
                modifier = Modifier.weight(1f),
                onClick = {
                    mainNavController.navigate(MainScreen.Progress.route)
                }
            )
        }
    }
}

@Composable
private fun HomeStatCard(
    title: String,
    value: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(150.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        onClick=onClick
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Icon(
                painter = painterResource(icon),
                contentDescription = title,
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}