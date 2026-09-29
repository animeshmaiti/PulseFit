package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.WorkoutHistoryDao
import com.animesh.pulsefit.data.entity.WorkoutHistory
import kotlinx.coroutines.flow.Flow

class WorkoutHistoryRepository(
    private val workoutHistoryDao: WorkoutHistoryDao
) {

    suspend fun createHistory(
        history: WorkoutHistory
    ): Long =
        workoutHistoryDao.insert(history)

    fun getAllHistory(): Flow<List<WorkoutHistory>> =
        workoutHistoryDao.getAllHistory()

    fun getLastWorkout(): Flow<WorkoutHistory?> =
        workoutHistoryDao.getLastWorkout()

    fun getCaloriesBetween(
        startOfDay: Long,
        endOfDay: Long
    ): Flow<Float> =
        workoutHistoryDao.getCaloriesBetween(
            startOfDay,
            endOfDay
        )

    fun getCompletionTimes(): Flow<List<Long>> =
        workoutHistoryDao.getCompletionTimes()
}