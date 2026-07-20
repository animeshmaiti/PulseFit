package com.animesh.pulsefit.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.animesh.pulsefit.R
import com.animesh.pulsefit.ui.navigation.MainScreen

@Composable
fun PulseFitTopBar(
    title: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = {
                navController.navigate(MainScreen.Profile.route) {
                    launchSingleTop = true
                }
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.account_circle_24px),
                contentDescription = "Profile"
            )
        }

        IconButton(
            onClick = {
                navController.navigate(MainScreen.Settings.route) {
                    launchSingleTop = true
                }
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.settings_24px),
                contentDescription = "Settings"
            )
        }
    }
}