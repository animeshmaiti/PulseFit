package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class Workout(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,

    val description: String = "",

    val isFavorite: Boolean = false,

    val isBuiltIn: Boolean = false
)