package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.WorkoutExerciseDao
import com.animesh.pulsefit.data.entity.WorkoutExercise
import kotlinx.coroutines.flow.Flow

class WorkoutExerciseRepository(
    private val workoutExerciseDao: WorkoutExerciseDao
) {

    fun getExercisesForWorkout(workoutId: Long): Flow<List<WorkoutExercise>> =
        workoutExerciseDao.getExercisesForWorkout(workoutId)

    suspend fun insertWorkoutExercise(
        workoutExercise: WorkoutExercise
    ): Long =
        workoutExerciseDao.insertWorkoutExercise(workoutExercise)

    suspend fun updateWorkoutExercise(
        workoutExercise: WorkoutExercise
    ) =
        workoutExerciseDao.updateWorkoutExercise(workoutExercise)

    suspend fun removeExerciseFromWorkout(
        workoutExercise: WorkoutExercise
    ) =
        workoutExerciseDao.deleteWorkoutExercise(workoutExercise)

    suspend fun clearWorkout(workoutId: Long) =
        workoutExerciseDao.deleteAllForWorkout(workoutId)
}