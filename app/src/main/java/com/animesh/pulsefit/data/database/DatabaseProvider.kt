package com.animesh.pulsefit.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: PulseFitDatabase? = null

    fun getDatabase(context: Context): PulseFitDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PulseFitDatabase::class.java,
                "pulsefit_database"
            )
                .fallbackToDestructiveMigration(false)
                .build()

            INSTANCE = instance
            instance
        }
    }
}