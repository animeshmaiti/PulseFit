package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val category: String,

    val description: String = "",

    // Calories estimation later
    val met: Float = 0f,

    val isFavorite: Boolean = false,

    // Built-in exercise or user-created
    val isBuiltIn: Boolean = false
)