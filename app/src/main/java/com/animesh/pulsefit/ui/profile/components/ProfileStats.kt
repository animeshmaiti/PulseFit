package com.animesh.pulsefit.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.R
import com.animesh.pulsefit.data.entity.UserProfile
import com.animesh.pulsefit.ui.profile.utils.formatValue

@Composable
fun ProfileStats(
    profile: UserProfile?
) {
    val weight = profile?.weightKg ?: 0f
    val age = profile?.age ?: 0
    val height = profile?.heightCm ?: 0f
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        ProfileStatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.weight_24px,
            value = if (weight > 0f) {
                "${weight.formatValue()} kg"
            } else {
                "--"
            },
            label = "Weight"
        )

        ProfileStatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.height_24px,
            value = if (height > 0f) {
                "${height.formatValue()} cm"
            } else {
                "--"
            },
            label = "Height"
        )

        ProfileStatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.cake_24px,
            value = if (age > 0) {
                "$age yrs"
            } else {
                "--"
            },
            label = "Age"
        )
    }
}