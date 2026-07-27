package com.animesh.pulsefit.data.repository

import androidx.room.withTransaction
import com.animesh.pulsefit.data.database.PulseFitDatabase
import com.animesh.pulsefit.data.entity.Workout
import com.animesh.pulsefit.data.entity.WorkoutExercise

class WorkoutBuilderRepository(
    private val database: PulseFitDatabase,
    private val workoutRepository: WorkoutRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository
) {

    suspend fun createWorkout(
        workout: Workout,
        exercises: List<WorkoutExercise>
    ): Long {

        return database.withTransaction {

            val workoutId =
                workoutRepository.createWorkout(workout)

            exercises.forEachIndexed { index, exercise ->

                workoutExerciseRepository.insertWorkoutExercise(
                    exercise.copy(
                        workoutId = workoutId,
                        position = index
                    )
                )
            }
            workoutId
        }
    }
}