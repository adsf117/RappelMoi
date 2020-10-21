package com.puzzlebench.rappelmoi.database

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface EvenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event): Long

    @Update
    fun update(event: Event)

    @Query("SELECT * FROM event_table ORDER BY id ")
    fun getAll(): PagingSource<Int, Event>

    @Query("SELECT * FROM event_table ")
    suspend fun getAllFeatureEvents(): List<Event>?

    @Query("SELECT * FROM event_table WHERE id = :key ")
    suspend fun getEventBy(key: Int): Event?

    @Query("DELETE FROM event_table")
    fun deleteAll()

    @Query("DELETE FROM event_table WHERE id = :key")
    fun deleteById(key: Int)
}