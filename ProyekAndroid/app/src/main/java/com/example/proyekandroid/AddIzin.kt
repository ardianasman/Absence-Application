package com.example.proyekandroid

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import kotlinx.android.synthetic.main.activity_add_cuti.*
import kotlinx.android.synthetic.main.activity_add_izin.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AddIzin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_izin)

        etdeskripsiizin.filters = arrayOf(InputFilter.LengthFilter(20))

        val sdf = SimpleDateFormat("EEEE, dd/MM/yyyy")
        val curdate = sdf.format(Date())
        tvStartIzin.setText(curdate.toString())
        tvEndIzin.setText(curdate.toString())


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var startdate = curdate.toString()
        var enddate = curdate.toString()
        var datediff = ""
        btnSaveIzin.isEnabled=false

        val StartdateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, monthOfYear)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            startdate = sdf.format(c.time)
            tvStartIzin.setText(startdate)
            datediff = (sdf.parse(enddate).time - sdf.parse(startdate).time).toString()


            if(TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS).toInt() == 0){
                btnSaveIzin.setText("Can't Apply for The Same Day")
                btnSaveIzin.isEnabled=false
            }
            else{
                btnSaveIzin.setText("Apply for " + TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS) + " Days Leave")
                btnSaveIzin.isEnabled=true
            }
        }
        val EnddateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, monthOfYear)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            enddate = sdf.format(c.time)
            tvEndIzin.setText(enddate)
            datediff = (sdf.parse(enddate).time - sdf.parse(startdate).time).toString()

            if(TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS).toInt() == 0){
                btnSaveIzin.setText("Can't Apply for The Same Day")
                btnSaveIzin.isEnabled=false
            }
            else{
                btnSaveIzin.setText("Apply for " + TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS) + " Days Leave")
                btnSaveIzin.isEnabled=true
            }
        }
        tvStartIzin.setOnClickListener {
            DatePickerDialog(this, StartdateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()

            startdate = sdf.format(c.time)
        }
        tvEndIzin.setOnClickListener {
            DatePickerDialog(this, EnddateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()

            enddate = sdf.format(c.time)
        }
        btnSaveIzin.setOnClickListener {


            db.collection("izin").document()
                .set(ClassCuti(
                    TimeUnit.DAYS.convert(datediff.toLong(), TimeUnit.MILLISECONDS).toString(),etdeskripsiizin.text.toString(),tvStartIzin.text.toString(),tvEndIzin.text.toString(),
                    MainActivity.data,"0"
                ))
            val intdone = Intent(this@AddIzin, Izin::class.java)
            startActivity(intdone)
            finish()
        }
    }
}