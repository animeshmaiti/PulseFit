package com.animesh.pulsefit.data.repository

import com.animesh.pulsefit.data.dao.ExerciseDao
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.enums.ExerciseCategory
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val exerciseDao: ExerciseDao
) {

    fun getAllExercises(): Flow<List<Exercise>> =
        exerciseDao.getAllExercises()

    fun getFavoriteExercises(): Flow<List<Exercise>> =
        exerciseDao.getFavoriteExercises()

    suspend fun getExerciseById(id: Long): Exercise? =
        exerciseDao.getExerciseById(id)

    suspend fun createExercise(exercise: Exercise): Long =
        exerciseDao.insertExercise(exercise)

    suspend fun updateExercise(exercise: Exercise) =
        exerciseDao.updateExercise(exercise)

    suspend fun deleteExercise(exercise: Exercise) =
        exerciseDao.deleteExercise(exercise)

    suspend fun toggleFavorite(exercise: Exercise) {
        exerciseDao.updateExercise(
            exercise.copy(
                isFavorite = !exercise.isFavorite
            )
        )
    }

    fun getExercisesByCategory(
        category: ExerciseCategory
    ): Flow<List<Exercise>> =
        exerciseDao.getExercisesByCategory(category)
}