package com.puzzlebench.rappelmoi.form

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.puzzlebench.rappelmoi.R
import com.puzzlebench.rappelmoi.database.RappelMoiDatabase
import kotlinx.android.synthetic.main.activity_form_reminder.*
import kotlinx.android.synthetic.main.activity_form_reminder.tv_date
import java.util.*


class FormReminderActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {


    private lateinit var viewModel: FromReminderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_reminder)
        val dataSource = RappelMoiDatabase.getInstance(this.applicationContext).evenDao
        val viewModelFactory = FromReminderViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FromReminderViewModel::class.java)
        val calendar: Calendar = Calendar.getInstance()
        tv_date.text =
            "${calendar.get(Calendar.DAY_OF_MONTH) - 1}-${calendar.get(Calendar.MONTH)}-${calendar.get(
                Calendar.MONTH
            )}"
        viewModel.viewStateLiveData.observe(::getLifecycle, ::handleViewState)
        tv_date.setOnClickListener {
            showDatePickerDialog()
        }
        tv_time.setOnClickListener {
            showTimePickerDialog()
        }
        btn_save.setOnClickListener {
            viewModel.saveEvent(
                et_name.text.toString()
                , et_description.text.toString(),
                tv_date.text.toString(),
                tv_time.text.toString()
            )
        }

    }

    private fun handleViewState(fromState: FormState) {
        when (fromState) {
            is FormState.ShowEmptyNameError -> {
                tf_name.error = getString(R.string.error_message_empty_name)
            }
            is FormState.ShowInvalidDateError -> {
                Toast.makeText(
                    this,
                    getString(R.string.error_message_invalid_date),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is FormState.SaveSuccessFull -> {
                tf_name.error = ""
            }
            is FormState.ShowMessage -> {
                Toast.makeText(this, fromState.message, Toast.LENGTH_SHORT).show()
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
        datePickerDialog.datePicker.minDate =
            calendar.apply { add(Calendar.DAY_OF_MONTH, -1) }.timeInMillis
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
        tv_date.text = "$day-$month-$year"
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        tv_time.text = "$hour:$minute"
    }
}