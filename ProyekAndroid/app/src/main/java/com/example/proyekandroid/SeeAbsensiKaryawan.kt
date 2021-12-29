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
        db.collection("user").whereEqualTo("email",KEY_USERNAME).get()
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    listAbsen.clear()
                    for(doc in task.result){
                        db.collection("absensi").whereEqualTo("username",doc.data["name"].toString()).get()
                            .addOnCompleteListener {task2->
                                if(task2.isSuccessful){
                                    for (doc2 in task2.result){
                                        listAbsen.add(doc2.toObject(Absensi::class.java))
                                    }
                                }
                            }
                    }
                }
                adapter= AbsenAdapter(listAbsen)
                rvAbsen.layoutManager=LinearLayoutManager(this)
                rvAbsen.adapter=adapter
            }
    }
}