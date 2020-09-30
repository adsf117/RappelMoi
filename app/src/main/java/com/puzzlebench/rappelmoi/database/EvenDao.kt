package com.puzzlebench.rappelmoi.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EvenDao {
    @Insert
    suspend fun insert(event: Event) : Long

    @Update
    fun update(event: Event)

    @Query("SELECT * FROM event_table ")
    suspend fun getAll(): List<Event>?

    @Query("SELECT * FROM event_table WHERE id = :key ")
    suspend fun getEventBy(key: Long): Event?

    @Query("DELETE FROM event_table")
    fun deleteAll()

    @Query("DELETE FROM event_table WHERE id = :key")
    fun deleteById(key: Long)
}