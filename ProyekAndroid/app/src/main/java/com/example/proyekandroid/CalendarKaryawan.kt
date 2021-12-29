package com.example.proyekandroid

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_account.navbaraccount
import kotlinx.android.synthetic.main.activity_calendar_karyawan.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.navbarhome
import java.util.*

private val arCalendar = arrayListOf<Pengumuman>()

class CalendarKaryawan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_karyawan)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        datePickerKaryawan.init(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        ) { datePicker, year, month, dayOfMonth ->
            getCalendar(dayOfMonth.toString() + "-" + (month + 1).toString() + "-" + year.toString())
        }


    }

    private fun getCalendar(datex : String){
        println(datex)
        db.collection("pengumuman")
            .get()
            .addOnSuccessListener { result ->
                arCalendar.clear()
                for(docs in result){
                    println(docs.data.get("tanggal").toString())
                    if(docs.data.get("tanggal").toString() == datex){
                        var data = Pengumuman(
                            docs.data.get("judul").toString(),"",""
                        )
                        arCalendar.add(data)
                    }
                }
                Tampilkan()
                println(arCalendar)
            }
    }

    private fun Tampilkan(){
        val adapterC = adaptercalendar(arCalendar)
        rvEvents.layoutManager = LinearLayoutManager(this)
        rvEvents.adapter = adapterC
    }
}