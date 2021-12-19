package com.example.proyekandroid

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import com.example.proyekandroid.MainActivity.Companion.data
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_cuti.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

class AddCuti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cuti)

        etdeskripsi.filters = arrayOf(InputFilter.LengthFilter(20))

        val sdf = SimpleDateFormat("EEEE, dd/MM/yyyy")
        val curdate = sdf.format(Date())
        tvStart.setText(curdate.toString())
        tvEnd.setText(curdate.toString())


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var startdate = curdate.toString()
        var enddate = curdate.toString()
        var datediff = ""
        btnSaveCuti.isEnabled=false


        val StartdateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, monthOfYear)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            startdate = sdf.format(c.time)
            tvStart.setText(startdate)
            datediff = (sdf.parse(enddate).time - sdf.parse(startdate).time).toString()


            if(TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS).toInt() == 0){
                btnSaveCuti.setText("Can't Apply for The Same Day")
                btnSaveCuti.isEnabled=false
            }
            else{
                btnSaveCuti.setText("Apply for " + TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS) + " Days Leave")
                btnSaveCuti.isEnabled=true
            }
        }
        val EnddateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, monthOfYear)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            enddate = sdf.format(c.time)
            tvEnd.setText(enddate)
            datediff = (sdf.parse(enddate).time - sdf.parse(startdate).time).toString()

            if(TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS).toInt() == 0){
                btnSaveCuti.setText("Can't Apply for The Same Day")
                btnSaveCuti.isEnabled=false
            }
            else{
                btnSaveCuti.setText("Apply for " + TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS) + " Days Leave")
                btnSaveCuti.isEnabled=true
            }
        }
        tvStart.setOnClickListener {
            DatePickerDialog(this, StartdateSetListener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)).show()

            startdate = sdf.format(c.time)
        }
        tvEnd.setOnClickListener {
            DatePickerDialog(this, EnddateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()

            enddate = sdf.format(c.time)
        }
        btnSaveCuti.setOnClickListener {


            db.collection("cuti").document()
                .set(ClassCuti(TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS).toString(),etdeskripsi.text.toString(),tvStart.text.toString(),tvEnd.text.toString(),data))
            val intdone = Intent(this@AddCuti, Cuti::class.java)
            startActivity(intdone)
        }
    }
}