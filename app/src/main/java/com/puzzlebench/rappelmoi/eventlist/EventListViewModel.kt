package com.puzzlebench.rappelmoi.eventlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.puzzlebench.rappelmoi.FetchEvents
import com.puzzlebench.rappelmoi.database.Event
import kotlinx.coroutines.launch


class EventListViewModel constructor(private val fetchEvents: FetchEvents) : ViewModel() {
    val allEvents = fetchEvents.invoke()

    private val _allEventsList = MutableLiveData<List<Event>>()
    val allEventsList: LiveData<List<Event>> get() = _allEventsList

    fun getEvents() {
        viewModelScope.launch {
            _allEventsList.value = fetchEvents.invokes()
        }
    }
}