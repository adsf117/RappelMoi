package com.puzzlebench.rappelmoi.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzlebench.rappelmoi.Event

class HomeViewModel : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _event

    fun getInfo() {
        val event = Event(
            "Acetaminophen",
            "Pain Killers",
            "Today At : 8:00 pm",
            repeat = true,
            repeatDetails = "Every 8 hours",
            completed = false
        )
        _event.value = event
    }
}