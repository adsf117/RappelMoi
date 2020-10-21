package com.puzzlebench.rappelmoi

import android.content.Intent
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule

import com.puzzlebench.rappelmoi.database.Event
import com.puzzlebench.rappelmoi.di.ServiceLocator
import com.puzzlebench.rappelmoi.eventlist.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule // unresolved reference here
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun createDB() {
        ServiceLocator.fetchEvents = FakeAndroidTestRepositoryEvents()
        activityRule.launchActivity(Intent())
    }

    @Test
    fun useAppContext() {
        Thread.sleep(5000)

        val itemPosition = 1
        Espresso.onView(withId(R.id.items_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("${NAME_EVENT}$itemPosition")),
                    ViewActions.click()
                )
            )

    }


}

const val NAME_EVENT = "Event"

class FakeAndroidTestRepositoryEvents : FetchEvents {
    override fun invoke(): Flow<PagingData<Event>> {
        return flow {
            emit(PagingData.from(getDummyListBusiness()))
            delay(10)
        }
    }

    private fun getDummyListBusiness(): List<
            Event> = (1..20).map {
        getDummyBusiness(it.toString())
    }

    private fun getDummyBusiness(seed: String) = Event(
        seed.toInt(),
        "${NAME_EVENT}$seed",
        "${"DummyBusinessFactory"}$seed",
        seed.toLong()
    )
}