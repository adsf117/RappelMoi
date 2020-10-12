package com.puzzlebench.rappelmoi

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.puzzlebench.rappelmoi.database.Event

@BindingAdapter("showDate")
fun TextView.showDate(item: Event?) {
    item?.let { event ->
        text = event.date.formatDate("HH:mm, MMM dd yyyy")
    }
}