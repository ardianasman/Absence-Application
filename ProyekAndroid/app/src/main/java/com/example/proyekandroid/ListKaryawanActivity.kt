package com.example.proyekandroid

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ListKaryawanActivity : AppCompatActivity() {
    val listItem: ArrayList<Karyawan> = ArrayList()
    lateinit var adapter: ListKaryawanAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_karyawan)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        adapter = ListKaryawanAdapter(listItem)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        readData(db)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun readData(db: FirebaseFirestore) {
        db.collection("user").get()
            .addOnSuccessListener { result ->
                listItem.clear()
                for (doc in result) {
                    val n = Karyawan(doc.data.get("email").toString(), doc.data.get("name").toString(), doc.data.get("password").toString(), doc.data.get("telp").toString())
                    listItem.add(n)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this@ListKaryawanActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}