package com.animesh.pulsefit

import android.app.Application
import com.animesh.pulsefit.data.database.DatabaseProvider
import com.animesh.pulsefit.data.database.DatabaseSeeder
import com.animesh.pulsefit.data.repository.ExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutExerciseRepository
import com.animesh.pulsefit.data.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log

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

    val workoutExerciseRepository by lazy {
        WorkoutExerciseRepository(database.workoutExerciseDao())
    }

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {

            val dao = database.exerciseDao()

            Log.d("PulseFit", "Count before = ${dao.getExerciseCount()}")

            if (dao.getExerciseCount() == 0) {
                dao.insertExercises(DatabaseSeeder.exercises)
                Log.d("PulseFit", "Database seeded")
            }

            Log.d("PulseFit", "Count after = ${dao.getExerciseCount()}")
        }
    }
}