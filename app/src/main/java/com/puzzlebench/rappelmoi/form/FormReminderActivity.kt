package com.puzzlebench.rappelmoi.form

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.puzzlebench.rappelmoi.R
import kotlinx.android.synthetic.main.activity_form_reminder.*
import java.util.*


class FormReminderActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_reminder)

        ti_details.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog()
            }
        }

    }

    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        calendar.add(Calendar.MONTH, 1)
        val now = System.currentTimeMillis() - 1000
        datePickerDialog.datePicker.minDate = now
        datePickerDialog.show()
    }


    private fun showTimePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this, this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(this)
        )
        timePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val date = "Selected Date : $day-$month-$year"
        tf_details.editText?.setText("$day-$month-$year")
        Toast.makeText(this, date, Toast.LENGTH_LONG).show()
        showTimePickerDialog()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        val date = "${ti_details.text}/ $hour:$minute"
        tf_details.editText?.setText(date)
        Toast.makeText(this, date, Toast.LENGTH_LONG).show()
    }
}