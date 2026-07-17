package com.animesh.pulsefit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.animesh.pulsefit.data.dao.ExerciseDao
import com.animesh.pulsefit.data.dao.WorkoutDao
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.entity.Workout
import com.animesh.pulsefit.data.entity.WorkoutExercise

@Database(
    entities = [
        Exercise::class,
        Workout::class,
        WorkoutExercise::class
    ],
    version = 1,
    exportSchema = true
)
abstract class PulseFitDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    abstract fun workoutDao(): WorkoutDao
}