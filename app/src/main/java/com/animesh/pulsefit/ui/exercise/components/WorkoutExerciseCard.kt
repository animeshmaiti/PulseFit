package com.animesh.pulsefit.ui.exercise.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.animesh.pulsefit.data.enums.BreakType
import com.animesh.pulsefit.ui.components.DurationPickerDialog
import com.animesh.pulsefit.ui.exercise.create.model.WorkoutExerciseUi

@Composable
fun WorkoutExerciseCard(
    workoutExercise: WorkoutExerciseUi,
    onRemove: () -> Unit,
    onEditDuration: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDurationPicker by remember {
        mutableStateOf(false)
    }

    if (showDurationPicker) {
        DurationPickerDialog(
            initialSeconds = workoutExercise.duration,
            onDismiss = {
                showDurationPicker = false
            },
            onConfirm = { seconds ->
                onEditDuration(seconds)
                showDurationPicker = false
            }
        )
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = workoutExercise.exercise.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (workoutExercise.exercise.description.isNotBlank()) {
                        Text(
                            text = workoutExercise.exercise.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = onRemove
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Remove Exercise"
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedButton(
                    onClick = {
                        showDurationPicker = true
                    }
                ) {
                    Text("${workoutExercise.duration}s")
                }

                OutlinedButton(
                    onClick = {
                        // TODO Change break
                    }
                ) {
                    Text(
                        when (workoutExercise.breakType) {
                            BreakType.NONE ->
                                "No Break"

                            BreakType.TIMER ->
                                "Timer"

                            BreakType.MANUAL ->
                                "${workoutExercise.breakDuration}s Break"
                        }
                    )
                }
            }
        }
    }
}