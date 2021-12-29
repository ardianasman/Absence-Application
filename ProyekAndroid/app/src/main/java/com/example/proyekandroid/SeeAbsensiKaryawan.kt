package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyekandroid.MainActivity.Companion.KEY_USERNAME
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list_karyawan.*

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
        db.collection("absensi").whereEqualTo("username",KEY_USERNAME).get()
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