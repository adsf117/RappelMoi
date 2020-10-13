package com.puzzlebench.rappelmoi

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.paging.PagedList
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule

import com.puzzlebench.rappelmoi.database.Event
import com.puzzlebench.rappelmoi.di.ServiceLocator
import com.puzzlebench.rappelmoi.eventlist.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    }
    @Test
    fun useAppContext() {
        Thread.sleep(5000)
        /*
        val itemPosition = 1
        Espresso.onView(withId(R.id.items_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("${NAME_EVENT}$itemPosition")),
                    ViewActions.click()
                )
            )*/

    }


}

const val NAME_EVENT = "Event"

class FakeAndroidTestRepositoryEvents : FetchEvents {

    private val allEventsMutable: MutableLiveData<PagedList<Event>> = MutableLiveData()
    private val allEventsLiveData: LiveData<PagedList<Event>>
        get() = allEventsMutable

    override fun invoke(): LiveData<PagedList<Event>> {
        allEventsMutable.value = getDummyListBusiness().asPagedList()
        return allEventsLiveData
    }

    override suspend fun invokes(): List<Event> =
        getDummyListBusiness()


    private fun getDummyListBusiness(): List<
            Event> = (1..20).map {
        getDummyBusiness(it.toString())
    }

    private fun getDummyBusiness(seed: String) = Event(
        seed.toLong(),
        "${NAME_EVENT}$seed",
        "${"DummyBusinessFactory.IMAGE_URL"}$seed",
        seed.toLong()
    )
}