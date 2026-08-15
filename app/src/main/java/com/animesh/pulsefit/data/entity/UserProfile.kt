package com.animesh.pulsefit.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(

    @PrimaryKey
    val id: Int = 1,

    val name: String = "",

    val age: Int = 0,

    val heightCm: Float = 0f,

    val weightKg: Float = 0f,

    val profileImageUri: String? = null,

    val medicalCondition: String = "",

    val bloodType: String = ""
)