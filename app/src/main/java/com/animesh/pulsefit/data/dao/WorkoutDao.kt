package com.animesh.pulsefit.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.animesh.pulsefit.data.entity.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout ORDER BY name ASC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE id = :id")
    suspend fun getWorkoutById(id: Long): Workout?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)
}