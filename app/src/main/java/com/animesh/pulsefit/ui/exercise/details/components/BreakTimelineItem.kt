package com.animesh.pulsefit.ui.exercise.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.viewmodel.utils.formatDuration

@Composable
fun BreakTimelineItem(
    breakDuration: Int
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp
            )
    ) {

        Text(
            text = "Break",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text =
            if (breakDuration > 0) {
                formatDuration(breakDuration)
            } else {
                "None"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}