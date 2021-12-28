package com.example.proyekandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import java.util.*
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener

import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker




class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        datePicker.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
        ) { datePicker, year, month, dayOfMonth ->
            Log.e(
                "Date",
                "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth
            )
        }
    }
}