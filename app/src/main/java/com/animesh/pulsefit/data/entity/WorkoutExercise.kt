package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.animesh.pulsefit.data.enums.BreakType

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
        Index("exerciseId"),
        Index(value = ["workoutId", "position"], unique = true)
    ]
)
data class WorkoutExercise(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val workoutId: Long,

    val exerciseId: Long,

    // Exercise timer (seconds)
    val duration: Int,

    // Order in workout
    val order: Int,

    // "NONE", "TIMER", "MANUAL"
    val breakType: BreakType = BreakType.NONE,

    // Seconds
    val breakDuration: Int = 0
)