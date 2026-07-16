package com.animesh.pulsefit.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.animesh.pulsefit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PulseFitTopBar(
    title: String,
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(

        title = {
            Text(text = title)
        },

        actions = {

            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(R.drawable.search_24px),
                    contentDescription = "Search"
                )
            }

            IconButton(onClick = onProfileClick) {
                Icon(
                    painter = painterResource(R.drawable.account_circle_24px),
                    contentDescription = "Profile"
                )
            }

            IconButton(onClick = onSettingsClick) {
                Icon(
                    painter = painterResource(R.drawable.settings_24px),
                    contentDescription = "Settings"
                )
            }
        }
    )
}