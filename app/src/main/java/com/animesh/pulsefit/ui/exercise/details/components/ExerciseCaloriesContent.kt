package com.animesh.pulsefit.ui.exercise.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animesh.pulsefit.ui.exercise.details.SessionState
import com.animesh.pulsefit.ui.theme.Orbitron
import java.util.Locale

@Composable
fun ExerciseCaloriesContent(
    calories: Float,
    sessionState: SessionState,
    hasCaloriesData: Boolean
) {

    val backgroundColor =
        when (sessionState) {

            SessionState.COUNTDOWN ->
                MaterialTheme.colorScheme.tertiaryContainer

            SessionState.RUNNING ->
                MaterialTheme.colorScheme.secondaryContainer

            SessionState.PAUSED ->
                MaterialTheme.colorScheme.surfaceContainerHighest

            else ->
                MaterialTheme.colorScheme.surfaceContainer
        }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {

        if (!hasCaloriesData) {

            Text(
                text = "MET value is not provided",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            Text(
                text = String.format(
                    Locale.getDefault(),
                    "%.2f kcal",
                    calories
                ),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontFamily = Orbitron,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 42.sp
                )
            )
        }
    }
}