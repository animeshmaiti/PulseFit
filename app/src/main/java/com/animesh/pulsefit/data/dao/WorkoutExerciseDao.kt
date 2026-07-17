package com.animesh.pulsefit.data.dao

import androidx.room.*
import com.animesh.pulsefit.data.entity.WorkoutExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseDao {

    @Query("""
        SELECT * FROM workout_exercise
        WHERE workoutId = :workoutId
        ORDER BY position ASC
    """)
    fun getExercisesForWorkout(workoutId: Long): Flow<List<WorkoutExercise>>

    @Query("""
    SELECT COALESCE(MAX(position), 0)
    FROM workout_exercise
    WHERE workoutId = :workoutId
    """)
    suspend fun getLastPosition(workoutId: Long): Int

    @Query("SELECT * FROM workout_exercise WHERE id = :id")
    suspend fun getWorkoutExerciseById(id: Long): WorkoutExercise?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutExercise(workoutExercise: WorkoutExercise): Long

    @Update
    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise)

    @Delete
    suspend fun deleteWorkoutExercise(workoutExercise: WorkoutExercise)

    @Query("DELETE FROM workout_exercise WHERE workoutId = :workoutId")
    suspend fun deleteAllForWorkout(workoutId: Long)
}