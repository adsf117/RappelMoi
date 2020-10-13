package com.puzzlebench.rappelmoi

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.puzzlebench.rappelmoi.database.EvenDao
import com.puzzlebench.rappelmoi.database.Event

const val PAGE_SIZE = 30

class FetchEventsImpl constructor(private val evenDao: EvenDao) : FetchEvents {
    override fun invoke(): LiveData<PagedList<Event>> = evenDao.getAll().toLiveData(PAGE_SIZE)
    override suspend fun invokes(): List<Event>? = evenDao.getAllFeatureEvents()
}