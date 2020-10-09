package com.puzzlebench.rappelmoi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 3, exportSchema = false)
abstract class RappelMoiDatabase : RoomDatabase() {

    abstract val evenDao: EvenDao

    companion object {
        @Volatile
        private var INSTANCE: RappelMoiDatabase? = null

        fun getInstance(context: Context): RappelMoiDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RappelMoiDatabase::class.java,
                        "RappelMoiDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return  instance
            }
        }
    }
}