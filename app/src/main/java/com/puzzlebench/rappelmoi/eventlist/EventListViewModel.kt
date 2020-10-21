package com.puzzlebench.rappelmoi.eventlist


import androidx.lifecycle.ViewModel
import com.puzzlebench.rappelmoi.FetchEvents

class EventListViewModel constructor(private val fetchEvents: FetchEvents) : ViewModel() {
    val allEvents = fetchEvents.invoke()
}