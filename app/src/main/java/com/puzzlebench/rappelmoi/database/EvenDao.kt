package com.puzzlebench.rappelmoi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

@Dao
interface EvenDao {
    @Insert
    suspend fun insert(event: Event)

    @Update
    fun update(event: Event)

    @Query("SELECT * FROM event_table ")
    suspend fun getAll(): List<Event>?

    @Query("DELETE FROM event_table")
    fun deleteAll()

    @Query("DELETE FROM event_table WHERE id = :key")
    fun deleteById(key: Long)
}