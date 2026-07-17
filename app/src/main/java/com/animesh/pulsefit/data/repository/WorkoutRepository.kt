package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.WorkoutDao
import com.animesh.pulsefit.data.entity.Workout
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(
    private val workoutDao: WorkoutDao
) {

    fun getAllWorkouts(): Flow<List<Workout>> =
        workoutDao.getAllWorkouts()

    fun getFavoriteWorkouts(): Flow<List<Workout>> =
        workoutDao.getFavoriteWorkouts()

    suspend fun getWorkoutById(id: Long): Workout? =
        workoutDao.getWorkoutById(id)

    suspend fun createWorkout(workout: Workout): Long =
        workoutDao.insertWorkout(workout)

    suspend fun updateWorkout(workout: Workout) =
        workoutDao.updateWorkout(workout)

    suspend fun deleteWorkout(workout: Workout) =
        workoutDao.deleteWorkout(workout)

    suspend fun toggleFavorite(workout: Workout) {
        workoutDao.updateWorkout(
            workout.copy(isFavorite = !workout.isFavorite)
        )
    }
}