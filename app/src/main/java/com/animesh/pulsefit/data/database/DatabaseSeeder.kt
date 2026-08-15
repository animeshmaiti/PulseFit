package com.animesh.pulsefit.data.database

import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.entity.UserProfile
import com.animesh.pulsefit.data.entity.Workout
import com.animesh.pulsefit.data.entity.WorkoutExercise
import com.animesh.pulsefit.data.enums.BreakType
import com.animesh.pulsefit.data.enums.ExerciseCategory

private data class WorkoutSeed(
    val name: String,
    val description: String = "",
    val exercises: List<WorkoutExerciseSeed>
)

private data class WorkoutExerciseSeed(
    val exerciseName: String,
    val duration: Int,
    val breakType: BreakType = BreakType.NONE,
    val breakDuration: Int = 0
)

object DatabaseSeeder {

    private val exercises = listOf(

        Exercise(
            name = "Bench Press",
            category = ExerciseCategory.CHEST,
            met = 6f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Push Up",
            category = ExerciseCategory.CHEST,
            met = 8f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Squat",
            category = ExerciseCategory.LEGS,
            met = 7f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Deadlift",
            category = ExerciseCategory.BACK,
            met = 8f,
            isBuiltIn = true
        ),

        Exercise(
            name = "Shoulder Press",
            category = ExerciseCategory.SHOULDERS,
            met = 6f,
            isBuiltIn = true
        )
    )

    private val workouts = listOf(

        Workout(
            name = "Push Day",
            description = "Chest and shoulder workout",
            isBuiltIn = true
        ),

        Workout(
            name = "Leg Day",
            description = "Lower body strength workout",
            isBuiltIn = true
        ),

        Workout(
            name = "Full Body",
            description = "Beginner full body routine",
            isBuiltIn = true
        )
    )

    private val workoutSeeds = listOf(

        WorkoutSeed(
            name = "Push Day",
            exercises = listOf(
                WorkoutExerciseSeed("Bench Press", 60),
                WorkoutExerciseSeed("Push Up", 45),
                WorkoutExerciseSeed("Shoulder Press", 45)
            )
        ),

        WorkoutSeed(
            name = "Leg Day",
            exercises = listOf(
                WorkoutExerciseSeed("Squat", 60),
                WorkoutExerciseSeed("Deadlift", 60)
            )
        ),

        WorkoutSeed(
            name = "Full Body",
            exercises = listOf(
                WorkoutExerciseSeed("Squat", 60),
                WorkoutExerciseSeed("Bench Press", 60),
                WorkoutExerciseSeed("Deadlift", 60)
            )
        )
    )
    private val defaultProfile = UserProfile(
        id = 1,
        name = "",
        age = 0,
        heightCm = 0f,
        weightKg = 0f
    )

    suspend fun seedDatabase(database: PulseFitDatabase) {

        val exerciseDao = database.exerciseDao()
        val workoutDao = database.workoutDao()
        val workoutExerciseDao = database.workoutExerciseDao()
        val userProfileDao = database.userProfileDao()

        if (userProfileDao.getProfile() == null) {
            userProfileDao.saveProfile(defaultProfile)
        }

        if (exerciseDao.getExerciseCount() > 0) {
            return
        }

        // Insert exercises
        exerciseDao.insertExercises(exercises)

        // Insert workouts
        workoutDao.insertWorkouts(workouts)

        // Create workout -> exercise relationships
        workoutSeeds.forEach { workoutSeed ->

            val workout =
                workoutDao.getWorkoutByName(workoutSeed.name)
                    ?: return@forEach

            workoutSeed.exercises.forEachIndexed { index, exerciseSeed ->

                val exercise =
                    exerciseDao.getExerciseByName(exerciseSeed.exerciseName)
                        ?: return@forEachIndexed

                workoutExerciseDao.insertWorkoutExercise(
                    WorkoutExercise(
                        workoutId = workout.id,
                        exerciseId = exercise.id,
                        duration = exerciseSeed.duration,
                        position = index,
                        breakType = exerciseSeed.breakType,
                        breakDuration = exerciseSeed.breakDuration
                    )
                )
            }
        }
    }
}