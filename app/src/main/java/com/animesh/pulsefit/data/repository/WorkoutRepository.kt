package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.WorkoutDao
import com.animesh.pulsefit.data.entity.Workout

class WorkoutRepository(
    private val workoutDao: WorkoutDao
) {

    val workouts = workoutDao.getAllWorkouts()

    val favorites = workoutDao.getFavoriteWorkouts()

    suspend fun insert(workout: Workout) =
        workoutDao.insertWorkout(workout)

    suspend fun update(workout: Workout) =
        workoutDao.updateWorkout(workout)

    suspend fun delete(workout: Workout) =
        workoutDao.deleteWorkout(workout)

    suspend fun getById(id: Long) =
        workoutDao.getWorkoutById(id)
}