package com.example.proyekandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekandroid.MainActivity.Companion.data
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cuti.*

private var arCuti = arrayListOf<ClassCuti>()


class Cuti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuti)

        btnAddCuti.setOnClickListener {
            val intaddcuti = Intent(this@Cuti, AddCuti::class.java)
            startActivity(intaddcuti)
        }

        getCuti()
    }

    private fun getCuti(){
        db.collection("cuti")
            .get()
            .addOnSuccessListener {result ->
                arCuti.clear()
                for(docs in result){
                    if(docs.data.get("username") == data){
                        var data = ClassCuti(
                        docs.data.get("durasi").toString() + " Days",
                        docs.data.get("keterangan").toString(),
                        docs.data.get("start").toString(),
                        "","",docs.data.get("status").toString()
                    )
                        arCuti.add(data)
                    }
                }
                TampilkanCuti()
            }
            .addOnFailureListener {
                println("PPPPPPPPPPPPPP")
            }
    }

    private fun TampilkanCuti(){
        val adapterC = adapterutama(arCuti)
        rvCuti.layoutManager = LinearLayoutManager(this)
        rvCuti.adapter = adapterC
    }
}