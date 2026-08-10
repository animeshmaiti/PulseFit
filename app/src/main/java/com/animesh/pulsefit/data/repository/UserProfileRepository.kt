package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.UserProfileDao
import com.animesh.pulsefit.data.entity.UserProfile

class UserProfileRepository(
    private val dao: UserProfileDao
) {

    suspend fun getProfile(): UserProfile? =
        dao.getProfile()

    suspend fun saveProfile(profile: UserProfile) =
        dao.saveProfile(profile)
}