package com.animesh.pulsefit.ui.profile.components

import androidx.compose.runtime.Composable
import com.animesh.pulsefit.R
import com.animesh.pulsefit.data.entity.UserProfile

@Composable
fun BloodTypeCard(
    profile: UserProfile?
) {

    ProfileInfoCard(
        icon = R.drawable.bloodtype_24px,
        title = "Blood Type",
        value = profile?.bloodType
            ?.takeIf { it.isNotBlank() }
            ?: "Not set"
    )
}