package com.animesh.pulsefit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.UserProfile
import com.animesh.pulsefit.data.repository.UserProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: UserProfileRepository
) : ViewModel() {

    var profile by mutableStateOf<UserProfile?>(null)
        private set

    fun loadProfile() {
        viewModelScope.launch {
            profile = repository.getProfile()
        }
    }

    fun saveProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            repository.saveProfile(updatedProfile)
            profile = updatedProfile
        }
    }

    companion object {

        fun factory(
            userProfileRepository: UserProfileRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return ProfileViewModel(
                        userProfileRepository
                    ) as T
                }
            }
    }
}