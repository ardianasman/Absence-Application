package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.proyekandroid.MainActivity.Companion.KEY_USERNAME
import com.google.firebase.firestore.FirebaseFirestore

class SetGaji : AppCompatActivity() {
    private var listKaryawan:MutableList<String> = mutableListOf()
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_gaji)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        db= FirebaseFirestore.getInstance()
        val spUser:Spinner=findViewById(R.id.spUser)
        val etPokok:EditText=findViewById(R.id.etPokok)
        val etBonus :EditText=findViewById(R.id.etBonus)
        val etDenda:EditText=findViewById(R.id.etDenda)
        val etLembur:EditText=findViewById(R.id.etLembur)
        val btnGaji: Button =findViewById(R.id.btnGaji)
        db.collection("user").get().addOnCompleteListener {task->
            if(task.isSuccessful){
                listKaryawan.clear()
                for(doc in task.result){
                    listKaryawan.add(doc.data["name"].toString())
                }
                val newAdapter=ArrayAdapter(this@SetGaji,android.R.layout.simple_spinner_item,listKaryawan)
                newAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spUser.adapter=newAdapter
            }
        }
        btnGaji.setOnClickListener {
            var pokok=etPokok.text.toString()
            var lembur=etLembur.text.toString()
            var bonus=etBonus.text.toString()
            var denda=etDenda.text.toString()
            var user=spUser.selectedItem.toString()
            db.collection("gaji").whereEqualTo("username",KEY_USERNAME).get()
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        for(doc in task.result){
                            doc.reference.update("gaji_pokok",pokok,"lembur",lembur,
                            "bonus",bonus,"denda",denda)
                        }
                    }
                }
        }
    }
}