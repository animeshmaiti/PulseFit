package com.animesh.pulsefit

import android.app.Application
import com.animesh.pulsefit.data.database.DatabaseProvider
import com.animesh.pulsefit.data.database.DatabaseSeeder
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.UserProfileRepository
import com.animesh.pulsefit.data.repository.WorkoutBuilderRepository
import com.animesh.pulsefit.data.repository.WorkoutExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PulseFitApplication : Application() {

    private val database by lazy {
        DatabaseProvider.getDatabase(this)
    }

    val exerciseRepository by lazy {
        ExerciseRepository(database.exerciseDao())
    }

    val workoutRepository by lazy {
        WorkoutRepository(database.workoutDao())
    }

    private val workoutExerciseRepository by lazy {
        WorkoutExerciseRepository(database.workoutExerciseDao())
    }
    val workoutBuilderRepository by lazy {
        WorkoutBuilderRepository(
            database = database,
            workoutRepository = workoutRepository,
            workoutExerciseRepository = workoutExerciseRepository
        )
    }
    val userProfileRepository by lazy {
        UserProfileRepository(database.userProfileDao())
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseSeeder.seedDatabase(database)
        }
    }
}