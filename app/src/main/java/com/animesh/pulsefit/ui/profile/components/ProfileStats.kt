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

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        ProfileStatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.weight_24px,
            value = if (profile?.weightKg ?: 0f > 0f) {
                "${profile?.weightKg?.formatValue()} kg"
            } else {
                "--"
            },
            label = "Weight"
        )

        ProfileStatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.height_24px,
            value = if (profile?.heightCm ?: 0f > 0f) {
                "${profile?.heightCm?.formatValue()} cm"
            } else {
                "--"
            },
            label = "Height"
        )

        ProfileStatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.cake_24px,
            value = if (profile?.age ?: 0 > 0) {
                "${profile?.age}"
            } else {
                "--"
            },
            label = "Age"
        )
    }
}