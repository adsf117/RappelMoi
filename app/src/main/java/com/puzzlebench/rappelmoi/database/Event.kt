package com.puzzlebench.rappelmoi.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_table")
class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val date: Long
)