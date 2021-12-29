package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cuti.*
import kotlinx.android.synthetic.main.activity_izin.*


private var arIzin = arrayListOf<ClassCuti>()

class Izin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_izin)

        btnAddIzin.setOnClickListener {
            val intizin = Intent(this@Izin, AddIzin::class.java)
            startActivity(intizin)
        }

        getCuti()
    }

    private fun getCuti(){
        db.collection("izin")
            .get()
            .addOnSuccessListener {result ->
                arIzin.clear()
                for(docs in result){
                    if(docs.data.get("username") == MainActivity.data){
                        var data = ClassCuti(
                        docs.data.get("durasi").toString() + " Days",
                        docs.data.get("keterangan").toString(),
                        docs.data.get("start").toString(),
                        "","",docs.data.get("status").toString()
                    )
                        arIzin.add(data)
                    }
                }
                TampilkanCuti()
            }
            .addOnFailureListener {
                println("PPPPPPPPPPPPPP")
            }
    }

    private fun TampilkanCuti(){
        val adapterC = adapterutama(arIzin)
        rvIzin.layoutManager = LinearLayoutManager(this)
        rvIzin.adapter = adapterC
    }

    override fun onBackPressed(){
        val intent = Intent(this@Izin, Home::class.java)
        startActivity(intent)
        finish()
    }
}