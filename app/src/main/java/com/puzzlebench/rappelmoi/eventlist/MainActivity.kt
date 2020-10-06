package com.puzzlebench.rappelmoi.eventlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.puzzlebench.rappelmoi.R
import com.puzzlebench.rappelmoi.database.RappelMoiDatabase
import com.puzzlebench.rappelmoi.databinding.ActivityMainBinding
import com.puzzlebench.rappelmoi.form.FormReminderActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: EventListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        val dataSource = RappelMoiDatabase.getInstance(this.applicationContext).evenDao
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
        viewModel.allEvents.observe(this, Observer(eventAdapter::submitList))

    }

    private fun goToForm(eventId: Long = 0) {
        startActivity(FormReminderActivity.getIntent(applicationContext, eventId))
    }
}