package com.animesh.pulsefit.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.animesh.pulsefit.data.entity.HomeStats
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(homeStats: HomeStats)

    @Query("""
        SELECT * FROM home_stats
        WHERE date = :date
        LIMIT 1
    """)
    suspend fun getByDate(date: String): HomeStats?

    @Query("""
        SELECT * FROM home_stats
        WHERE date = :date
        LIMIT 1
    """)
    fun observeByDate(date: String): Flow<HomeStats?>

    @Query("""
        SELECT * FROM home_stats
        ORDER BY date DESC
    """)
    fun getAll(): Flow<List<HomeStats>>
}