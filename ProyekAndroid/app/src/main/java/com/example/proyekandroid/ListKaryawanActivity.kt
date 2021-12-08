package com.example.proyekandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListKaryawanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_karyawan)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val listItem: ArrayList<Karyawan> = ArrayList()
        val rv = findViewById<RecyclerView>(R.id.rv)
        val adapter = ListKaryawanAdapter(listItem)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }
}