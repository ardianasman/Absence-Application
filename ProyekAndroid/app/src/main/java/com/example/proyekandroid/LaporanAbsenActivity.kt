package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LaporanAbsenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_absen)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val listAbsen=ArrayList<Absen>()
        val adapterAbsen=AbsenAdapter(listAbsen)
        val listKaryawan= ArrayList<String>()
        val listBulan=ArrayList<String>()
        val spKaryawan : Spinner=findViewById(R.id.spKaryawan)
        val spBulan :Spinner=findViewById(R.id.spBulan)
        val rvList:RecyclerView=findViewById(R.id.rvAbsen)
        rvList.adapter=adapterAbsen
        rvList.layoutManager=LinearLayoutManager(this)

    }
}