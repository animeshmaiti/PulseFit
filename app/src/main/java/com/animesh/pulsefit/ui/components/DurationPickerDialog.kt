package com.animesh.pulsefit.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import com.animesh.pulsefit.ui.components.picktime.PickHourMinuteSecond


@Composable
fun DurationPickerDialog(
    initialSeconds: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {

    var hour by remember(initialSeconds) {
        mutableIntStateOf(initialSeconds / 3600)
    }

    var minute by remember(initialSeconds) {
        mutableIntStateOf((initialSeconds % 3600) / 60)
    }

    var second by remember(initialSeconds) {
        mutableIntStateOf(initialSeconds % 60)
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = "Exercise Duration",
                style = MaterialTheme.typography.titleLarge
            )
        },

        text = {

            PickHourMinuteSecond(
                initialHour = hour,
                onHourChange = { hour = it },

                initialMinute = minute,
                onMinuteChange = { minute = it },

                initialSecond = second,
                onSecondChange = { second = it },

                isLooping = true
            )
        },

        confirmButton = {

            TextButton(
                onClick = {

                    val totalSeconds =
                        hour * 3600 +
                                minute * 60 +
                                second

                    onConfirm(totalSeconds)
                }
            ) {
                Text("OK")
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}