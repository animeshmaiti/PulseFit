package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_history")
data class WorkoutHistory(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val workoutId: Long,

    val workoutName: String,

    // Unix timestamp in milliseconds
    val completedAt: Long,

    // Total exercise time in seconds
    val totalExerciseTime: Int,

    // Total session time including breaks
    val totalSessionTime: Int,

    // Calories burned during the workout
    val caloriesBurned: Float
)