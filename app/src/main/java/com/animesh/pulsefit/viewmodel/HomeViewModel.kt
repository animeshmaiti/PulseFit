package com.animesh.pulsefit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.animesh.pulsefit.data.entity.HomeStats
import com.animesh.pulsefit.data.repository.HomeStatsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(
    private val homeStatsRepository: HomeStatsRepository
) : ViewModel() {

    private val today = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.US
    ).format(Date())

    val homeStats: StateFlow<HomeStats?> =
        homeStatsRepository
            .observeByDate(today)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    companion object {

        fun factory(
            homeStatsRepository: HomeStatsRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {

                    return HomeViewModel(
                        homeStatsRepository
                    ) as T
                }
            }
    }
}