package com.puzzlebench.rappelmoi.eventlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.puzzlebench.rappelmoi.R
import com.puzzlebench.rappelmoi.components.RappelMoiApplication
import com.puzzlebench.rappelmoi.databinding.ActivityMainBinding
import com.puzzlebench.rappelmoi.form.FormReminderActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EventListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        val dataSource = (this.applicationContext as RappelMoiApplication).fetchEvents
        val viewModelFactory = EventListViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EventListViewModel::class.java)
        val eventAdapter = EventListAdapter(EventListener { eventId ->
            goToForm(eventId)
        })
        val manager = GridLayoutManager(this, 1)
        binding.itemsList.apply {
            layoutManager = manager
            adapter = eventAdapter
        }
        binding.navigateToFrom.setOnClickListener {
            goToForm()
        }
        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes
        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.allEvents.collectLatest { eventAdapter.submitData(it) }
        }
    }

    private fun goToForm(eventId: Int = 0) {
        startActivity(FormReminderActivity.getIntent(applicationContext, eventId))
    }
}