package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_exercise",
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("workoutId"),
        Index("exerciseId")
    ]
)
data class WorkoutExercise(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val workoutId: Long,

    val exerciseId: Long,

    // Order in workout
    val position: Int,

    // "NONE", "TIMER", "MANUAL"
    val breakType: String = "NONE",

    // Seconds
    val breakDuration: Int = 0
)