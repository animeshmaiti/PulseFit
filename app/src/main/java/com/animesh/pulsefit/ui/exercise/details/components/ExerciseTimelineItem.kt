package com.animesh.pulsefit.ui.exercise.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.viewmodel.utils.formatDuration

@Composable
fun ExerciseTimelineItem(
    title:String,
    duration: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                    MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement =
                    Arrangement.spacedBy(4.dp)
                ) {

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )


                    Text(
                        text = formatDuration(duration),
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}