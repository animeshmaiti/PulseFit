package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_history",
    indices = [
        Index("exerciseId"),
        Index("completedAt")
    ]
)
data class ExerciseHistory(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val exerciseId: Long,

    val exerciseName: String,

    // Duration actually performed, in seconds
    val duration: Int,

    // Calories burned during the exercise
    val caloriesBurned: Float,

    // Unix timestamp in milliseconds
    val completedAt: Long
)