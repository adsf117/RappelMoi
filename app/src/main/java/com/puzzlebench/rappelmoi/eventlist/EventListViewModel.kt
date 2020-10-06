package com.puzzlebench.rappelmoi.eventlist

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.puzzlebench.rappelmoi.database.EvenDao


const val PAGE_SIZE = 30
const val ENABLE_PLACEHOLDERS = true

class EventListViewModel constructor(private val eventDao: EvenDao) : ViewModel() {

    val allEvents = LivePagedListBuilder(
        eventDao.getAll(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
            .build()
    ).build()

}