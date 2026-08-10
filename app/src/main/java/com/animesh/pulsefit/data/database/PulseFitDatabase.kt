package com.animesh.pulsefit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.animesh.pulsefit.data.dao.ExerciseDao
import com.animesh.pulsefit.data.dao.WorkoutDao
import com.animesh.pulsefit.data.dao.WorkoutExerciseDao
import com.animesh.pulsefit.data.entity.Exercise
import com.animesh.pulsefit.data.entity.Workout
import com.animesh.pulsefit.data.entity.WorkoutExercise
import androidx.room.TypeConverters
import com.animesh.pulsefit.data.converters.Converters
import com.animesh.pulsefit.data.dao.UserProfileDao
import com.animesh.pulsefit.data.entity.UserProfile

@TypeConverters(Converters::class)
@Database(
    entities = [
        Exercise::class,
        Workout::class,
        WorkoutExercise::class,
        UserProfile::class
    ],
    version = 2,
    exportSchema = true
)
abstract class PulseFitDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun userProfileDao(): UserProfileDao
}