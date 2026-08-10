package com.animesh.pulsefit.ui.profile.components

import androidx.compose.runtime.Composable
import com.animesh.pulsefit.R
import com.animesh.pulsefit.data.entity.UserProfile

@Composable
fun MedicalConditionCard(
    profile: UserProfile?
) {

    ProfileInfoCard(
        icon = R.drawable.medical_24px,
        title = "Medical Condition",
        value = profile?.medicalCondition
            ?.takeIf { it.isNotBlank() }
            ?: "None"
    )
}