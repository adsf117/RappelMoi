package com.puzzlebench.rappelmoi


import androidx.paging.PagingData
import com.puzzlebench.rappelmoi.database.Event
import kotlinx.coroutines.flow.Flow

interface FetchEvents {
    fun invoke(): Flow<PagingData<Event>>
}