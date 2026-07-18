package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.animesh.pulsefit.data.enums.ExerciseCategory

@Entity(tableName = "exercise")
data class Exercise(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val category: ExerciseCategory,

    val description: String = "",

    // Metabolic Equivalent of Task (used for cal
    // orie estimation)
    val met: Float = 0f,

    val isFavorite: Boolean = false,

    // Built-in exercise or user-created
    val isBuiltIn: Boolean = false
)