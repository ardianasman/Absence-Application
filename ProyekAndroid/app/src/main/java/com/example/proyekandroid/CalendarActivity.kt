package com.example.proyekandroid

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import java.util.*
import android.widget.DatePicker.OnDateChangedListener
import androidx.appcompat.app.AlertDialog

import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat


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
            val builder = AlertDialog.Builder(this)
            val dialogView = LayoutInflater.from(this@CalendarActivity).inflate(R.layout.dialog_input_pengumuman, null, false)
            val edJudul = dialogView.findViewById<EditText>(R.id.edJudul)
            val edText = dialogView.findViewById<EditText>(R.id.edText)
            builder.setPositiveButton("Input") { dialog, _ ->
                val jdl = edJudul.text.toString()
                val txt = edText.text.toString()
                var cancontinue = true
                var errMsg = ""
                if (jdl.isEmpty()) {
                    cancontinue = false
                    errMsg += "Harap mengisi judul!\n"
                }
                if (txt.isEmpty()) {
                    cancontinue = false
                    errMsg += "Harap mengisi konten pengumuman!"
                }
                if (cancontinue == false) {
                    Toast.makeText(this, errMsg, Toast.LENGTH_LONG).show()
                } else {
                    val tgl = "$dayOfMonth-${(month + 1)}-$year"
                    val waktu = SimpleDateFormat("HH:mm:ss").format(Date())
                    val db = FirebaseFirestore.getInstance()
                    val pengumuman = Pengumuman(jdl, tgl, txt)
                    db.collection("pengumuman").document(tgl + "_" + waktu)
                        .set(pengumuman)
                    Toast.makeText(this, "Input pengumuman sukses!", Toast.LENGTH_LONG).show()
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setView(dialogView)
            val alert = builder.create()
            alert.show()

        }
    }
}