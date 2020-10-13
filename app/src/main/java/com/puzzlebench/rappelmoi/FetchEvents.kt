package com.puzzlebench.rappelmoi

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.puzzlebench.rappelmoi.database.Event

interface FetchEvents {
    fun invoke(): LiveData<PagedList<Event>>

    suspend fun invokes(): List<Event>?
}