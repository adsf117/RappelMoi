package com.puzzlebench.rappelmoi

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(formatExpected: String): String {

    val dateFormat = SimpleDateFormat(formatExpected,Locale.getDefault());
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return dateFormat.format(calendar.time)
}