package com.puzzlebench.rappelmoi.di

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.puzzlebench.rappelmoi.FetchEvents
import com.puzzlebench.rappelmoi.FetchEventsImpl
import com.puzzlebench.rappelmoi.database.DB_NAME
import com.puzzlebench.rappelmoi.database.EvenDao
import com.puzzlebench.rappelmoi.database.RappelMoiDatabase

object ServiceLocator {

    private var database: RappelMoiDatabase? = null

    @Volatile
    var fetchEvents: FetchEvents? = null
        @VisibleForTesting set

    fun provideFetchEvents(context: Context): FetchEvents {
        synchronized(this) {
            return fetchEvents?: FetchEventsImpl(provideEventDao(context))
        }
    }

   private fun provideEventDao(context: Context): EvenDao {
        synchronized(this) {
            val database = database
                ?: createDataBase(context)
            return database.evenDao
        }
    }

    private fun createDataBase(context: Context): RappelMoiDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            RappelMoiDatabase::class.java, DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
        database = result
        return result
    }

}