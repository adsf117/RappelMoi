package com.puzzlebench.rappelmoi.eventlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.puzzlebench.rappelmoi.database.Event
import com.puzzlebench.rappelmoi.databinding.EventItemBinding

class EventListAdapter(private val clickListener: EventListener) :
    PagedListAdapter<Event, RecyclerView.ViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as EventViewHolder).bind(clickListener, it)
        }

    }

    class EventViewHolder(private val biding: EventItemBinding) :
        RecyclerView.ViewHolder(biding.root) {
        fun bind(clickEventListener: EventListener, item: Event) {
            biding.apply {
                event = item
                clickListener = clickEventListener
                executePendingBindings()
            }
        }
    }
}

private class EventDiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}

class EventListener(val clickListener: (eventId: Long) -> Unit) {
    fun onClick(event: Event) = clickListener(event.id)
}