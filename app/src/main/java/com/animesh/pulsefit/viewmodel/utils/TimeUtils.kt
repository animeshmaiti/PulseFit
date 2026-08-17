package com.animesh.pulsefit.viewmodel.utils

import java.util.Locale


fun Int.toHmsString(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return "%02d:%02d:%02d".format(
        hours,
        minutes,
        seconds
    )
}

fun formatDuration(
    seconds: Int
): String {

    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60

    return when {

        hours > 0 ->
            String.format(
                Locale.getDefault(),
                "%d:%02d:%02d",
                hours,
                minutes,
                remainingSeconds
            )

        else ->
            String.format(
                Locale.getDefault(),
                "%02d:%02d",
                minutes,
                remainingSeconds
            )
    }
}