package com.puzzlebench.rappelmoi.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzlebench.rappelmoi.database.EvenDao
import com.puzzlebench.rappelmoi.database.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class FromReminderViewModel constructor(private val evenDao: EvenDao) : ViewModel() {

    private val viewStateMutableLiveData = MutableLiveData<FormState>()

    val viewStateLiveData: LiveData<FormState>
        get() = viewStateMutableLiveData

    fun saveEvent(name: String, description: String, date: String, dateTime: String) {
        if (name.isEmpty()) {
            viewStateMutableLiveData.value =
                FormState.ShowEmptyNameError
            return
        } else if (Calendar.getInstance().apply { add(Calendar.HOUR, -12) }.time.after(
                getCalendarSelected(date, dateTime).time
            )
        ) {
            viewStateMutableLiveData.value =
                FormState.ShowInvalidDateError
            return
        }
        viewModelScope.launch {
            val event = Event(
                name = name,
                description = description,
                date = getCalendarSelected(date, dateTime).timeInMillis
            )
            val savedEvent = event.copy(id = saveEvent(event))
            viewStateMutableLiveData.value = FormState.SaveSuccessFull(savedEvent)
        }

    }

    private suspend fun saveEvent(event: Event) = withContext(Dispatchers.IO) {
        return@withContext evenDao.insert(event)
    }


    private fun getCalendarSelected(date: String, dateTime: String): Calendar {
        val dateSelected = date.split("-")
        val dateTimeSelected = dateTime.split(":")
        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.DAY_OF_MONTH, dateSelected[0].toInt())
            set(Calendar.MONTH, dateSelected[1].toInt())
            set(Calendar.YEAR, dateSelected[2].toInt())
            set(Calendar.HOUR, dateTimeSelected[0].toInt())
            set(Calendar.MINUTE, dateTimeSelected[1].toInt())
            set(Calendar.SECOND, 0)
        }
    }
}