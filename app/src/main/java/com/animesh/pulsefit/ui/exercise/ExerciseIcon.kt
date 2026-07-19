package com.animesh.pulsefit.ui.exercise

import androidx.annotation.DrawableRes
import com.animesh.pulsefit.R
import com.animesh.pulsefit.data.enums.ExerciseCategory

@DrawableRes
fun ExerciseCategory.icon(): Int =
    when (this) {
        ExerciseCategory.CHEST -> R.drawable.chest
        ExerciseCategory.BACK -> R.drawable.back_24px
        ExerciseCategory.SHOULDERS -> R.drawable.shoulder_24px
        ExerciseCategory.LEGS -> R.drawable.legs_24px
        ExerciseCategory.ARMS -> R.drawable.arms_24px
        ExerciseCategory.CORE -> R.drawable.core_24px
        ExerciseCategory.CARDIO -> R.drawable.cardio_load_24px
        ExerciseCategory.FULL_BODY -> R.drawable.fullbody_24px
        ExerciseCategory.OTHER -> R.drawable.exercise_24px
    }