package com.animesh.pulsefit.data.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_stats")
data class HomeStats(

    @PrimaryKey
    val date: String,

    // Calories burned today
    val caloriesBurned: Float = 0f,

    // Daily calorie target
    val calorieTarget: Float = 500f, // TODO: make user configurable

    // Current streak count for this day
    val streak: Int = 0,

    // True only when caloriesBurned >= calorieTarget
    val isActive: Boolean = false,

    // Last completed solo exercise
    val lastExerciseId: Long? = null,
    val lastExerciseName: String? = null,

    // Last completed workout
    val lastWorkoutId: Long? = null,
    val lastWorkoutName: String? = null
)