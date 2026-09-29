package com.animesh.pulsefit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.animesh.pulsefit.data.entity.WorkoutHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutHistoryDao {

    @Insert
    suspend fun insert(history: WorkoutHistory): Long

    @Query("""
        SELECT * FROM workout_history
        ORDER BY completedAt DESC
    """)
    fun getAllHistory(): Flow<List<WorkoutHistory>>

    @Query("""
        SELECT * FROM workout_history
        ORDER BY completedAt DESC
        LIMIT 1
    """)
    fun getLastWorkout(): Flow<WorkoutHistory?>

    @Query("""
        SELECT COALESCE(SUM(caloriesBurned), 0)
        FROM workout_history
        WHERE completedAt >= :startOfDay
        AND completedAt < :endOfDay
    """)
    fun getCaloriesBetween(
        startOfDay: Long,
        endOfDay: Long
    ): Flow<Float>

    @Query("""
        SELECT completedAt
        FROM workout_history
        ORDER BY completedAt DESC
    """)
    fun getCompletionTimes(): Flow<List<Long>>
}