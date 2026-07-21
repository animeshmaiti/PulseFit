package com.animesh.pulsefit.ui.extensions

import com.animesh.pulsefit.data.enums.ExerciseCategory

fun ExerciseCategory.displayName(): String =
    when (this) {
        ExerciseCategory.CHEST -> "Chest"
        ExerciseCategory.CARDIO -> "Cardio"
        ExerciseCategory.BACK -> "Back"
        ExerciseCategory.SHOULDERS -> "Shoulders"
        ExerciseCategory.LEGS -> "Legs"
        ExerciseCategory.ARMS -> "Arms"
        ExerciseCategory.CORE -> "Core"
        ExerciseCategory.FULL_BODY -> "Full Body"
        ExerciseCategory.OTHER -> "Other"
    }