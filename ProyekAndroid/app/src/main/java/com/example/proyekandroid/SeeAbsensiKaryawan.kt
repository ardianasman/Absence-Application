package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SeeAbsensiKaryawan : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private var rvAbsen=findViewById<RecyclerView>(R.id.rvAbsenKaryawan)
    private lateinit var adapter:AbsenAdapter
    private var listAbsen: ArrayList<Absensi> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_absensi_karyawan)
        db= FirebaseFirestore.getInstance()
        adapter= AbsenAdapter(listAbsen)
        rvAbsen.layoutManager=LinearLayoutManager(this)
        rvAbsen.adapter=adapter
        db.collection("absensi").whereEqualTo("username", KEY_USERNAME).get()
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    listAbsen.clear()
                    for(doc in task.result){
                        listAbsen.add(doc.toObject(Absensi::class.java))
                    }
                }
            }
    }
}