package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.ExerciseHistoryDao
import com.animesh.pulsefit.data.entity.ExerciseHistory
import kotlinx.coroutines.flow.Flow

class ExerciseHistoryRepository(
    private val exerciseHistoryDao: ExerciseHistoryDao
) {

    suspend fun createHistory(
        history: ExerciseHistory
    ): Long =
        exerciseHistoryDao.insert(history)

    fun getLastExercise(): Flow<ExerciseHistory?> =
        exerciseHistoryDao.getLastExercise()
}