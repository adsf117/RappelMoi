package com.puzzlebench.rappelmoi.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puzzlebench.rappelmoi.components.EventHelperAlarmManager
import com.puzzlebench.rappelmoi.database.EvenDao

@Suppress("UNCHECKED_CAST")
class FromReminderViewModelFactory(
    private val evenDao: EvenDao,
    private val eventHelperAlarmManager: EventHelperAlarmManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FromReminderViewModel(evenDao, eventHelperAlarmManager) as T
    }
}