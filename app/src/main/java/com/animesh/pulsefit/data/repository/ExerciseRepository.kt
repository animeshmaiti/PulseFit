package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.ExerciseDao
import com.animesh.pulsefit.data.entity.Exercise

class ExerciseRepository(
    private val exerciseDao: ExerciseDao
) {

    val exercises = exerciseDao.getAllExercises()

    val favorites = exerciseDao.getFavoriteExercises()

    suspend fun insert(exercise: Exercise) =
        exerciseDao.insertExercise(exercise)

    suspend fun update(exercise: Exercise) =
        exerciseDao.updateExercise(exercise)

    suspend fun delete(exercise: Exercise) =
        exerciseDao.deleteExercise(exercise)

    suspend fun getById(id: Long) =
        exerciseDao.getExerciseById(id)
}