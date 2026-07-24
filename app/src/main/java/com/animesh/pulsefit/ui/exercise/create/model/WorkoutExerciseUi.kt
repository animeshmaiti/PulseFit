package com.animesh.pulsefit.ui.exercise.create.model

import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.enums.BreakType

data class WorkoutExerciseUi(

    val exercise: Exercise,

    val duration: Int = 30,

    val breakType: BreakType = BreakType.NONE,

    val breakDuration: Int = 0
)