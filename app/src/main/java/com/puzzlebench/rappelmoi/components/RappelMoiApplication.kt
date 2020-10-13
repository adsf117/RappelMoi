package com.puzzlebench.rappelmoi.components

import android.app.Application
import com.puzzlebench.rappelmoi.FetchEvents
import com.puzzlebench.rappelmoi.di.ServiceLocator

class RappelMoiApplication : Application() {

    val fetchEvents: FetchEvents
        get() = ServiceLocator.provideFetchEvents(this)
}