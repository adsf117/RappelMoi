package com.puzzlebench.rappelmoi.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puzzlebench.rappelmoi.database.EvenDao

@Suppress("UNCHECKED_CAST")
class EventListViewModelFactory constructor(private val eventDao: EvenDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventListViewModel(eventDao) as T
    }
}