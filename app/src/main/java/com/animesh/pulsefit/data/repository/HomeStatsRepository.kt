package com.animesh.pulsefit.data.repository

import android.util.Log
import com.animesh.pulsefit.data.dao.HomeStatsDao
import com.animesh.pulsefit.data.entity.HomeStats
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeStatsRepository(
    private val homeStatsDao: HomeStatsDao
) {

    suspend fun save(homeStats: HomeStats) =
        homeStatsDao.insertOrUpdate(homeStats)

    suspend fun getByDate(date: String): HomeStats? =
        homeStatsDao.getByDate(date)

    fun observeByDate(date: String): Flow<HomeStats?> =
        homeStatsDao.observeByDate(date)

    fun getAll(): Flow<List<HomeStats>> =
        homeStatsDao.getAll()

    suspend fun addCalories(
        calories: Float,
        exerciseId: Long? = null,
        exerciseName: String? = null,
        workoutId: Long? = null,
        workoutName: String? = null
    ) {
        val today = getDateString()

        val current = getByDate(today)

        val newCalories =
            (current?.caloriesBurned ?: 0f) + calories

        val target =
            current?.calorieTarget ?: 500f

        val wasActive =
            current?.isActive ?: false

        val isActive =
            newCalories >= target

        val streak =
            when {
                !isActive -> 0

                wasActive -> {
                    current?.streak ?: 1
                }

                else -> {
                    calculateNewStreak(today)
                }
            }

        val updatedStats = HomeStats(
            date = today,

            caloriesBurned = newCalories,

            calorieTarget = target,

            streak = streak,

            isActive = isActive,

            lastExerciseId =
                exerciseId ?: current?.lastExerciseId,

            lastExerciseName =
                exerciseName ?: current?.lastExerciseName,

            lastWorkoutId =
                workoutId ?: current?.lastWorkoutId,

            lastWorkoutName =
                workoutName ?: current?.lastWorkoutName
        )

        save(updatedStats)

    }

    private suspend fun calculateNewStreak(
        today: String
    ): Int {

        val calendar = Calendar.getInstance()

        var streak = 1

        calendar.add(Calendar.DAY_OF_YEAR, -1)

        while (true) {

            val previousDate =
                getDateString(calendar.time)

            val previousStats =
                getByDate(previousDate)

            if (previousStats?.isActive != true) {
                break
            }

            streak++

            calendar.add(
                Calendar.DAY_OF_YEAR,
                -1
            )
        }

        return streak
    }

    private fun getDateString(): String =
        getDateString(Date())

    private fun getDateString(date: Date): String =
        SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).format(date)
}