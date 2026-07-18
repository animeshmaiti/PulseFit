package com.animesh.pulsefit.data.database

import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.enums.ExerciseCategory

object DatabaseSeeder {

    val exercises = listOf(

        Exercise(
            name = "Bench Press",
            category = ExerciseCategory.CHEST,
            met = 6f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Push Up",
            category = ExerciseCategory.CHEST,
            met = 8f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Squat",
            category = ExerciseCategory.LEGS,
            met = 7f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Deadlift",
            category = ExerciseCategory.BACK,
            met = 8f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Shoulder Press",
            category = ExerciseCategory.SHOULDERS,
            met = 6f,
            isBuiltIn = true
        )
    )
}