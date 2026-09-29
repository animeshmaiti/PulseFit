package com.animesh.pulsefit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.animesh.pulsefit.data.entity.ExerciseHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseHistoryDao {

    @Insert
    suspend fun insert(history: ExerciseHistory): Long

    @Query("""
        SELECT * FROM exercise_history
        ORDER BY completedAt DESC
        LIMIT 1
    """)
    fun getLastExercise(): Flow<ExerciseHistory?>
}