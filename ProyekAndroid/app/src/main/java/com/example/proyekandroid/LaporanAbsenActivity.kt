package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class LaporanAbsenActivity : AppCompatActivity() {
    private lateinit var db :FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_absen)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        db= FirebaseFirestore.getInstance()
        val listAbsen=ArrayList<Absensi>()
        val adapterAbsen=AbsenAdapter(listAbsen)
        val listKaryawan= ArrayList<String>()
        val listBulan= arrayListOf("","Januari","Februari","Maret","April","Mei","Juni",
        "Juli","Agustus","September","Oktober","November","Desember")
        val spKaryawan : Spinner=findViewById(R.id.spKaryawan)
        val spBulan :Spinner=findViewById(R.id.spBulan)
        val rvList:RecyclerView=findViewById(R.id.rvAbsen)
        rvList.adapter=adapterAbsen
        rvList.layoutManager=LinearLayoutManager(this)
        val adapterBulan=ArrayAdapter(this@LaporanAbsenActivity,android.R.layout.simple_spinner_item,listBulan)
        db.collection("user").get().addOnCompleteListener {task->
            if (task.isSuccessful){
                listKaryawan.clear()
                for (doc in task.result){
                    listKaryawan.add(doc.data["name"].toString())
                }
                val adapterKaryawan=ArrayAdapter(this@LaporanAbsenActivity,android.R.layout.simple_spinner_item,listKaryawan)
                adapterKaryawan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spKaryawan.adapter =adapterKaryawan
            }
        }
        spKaryawan.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                db.collection("absensi").whereEqualTo("username",spKaryawan.selectedItem.toString())
                    .get().addOnCompleteListener {task->
                        if (task.isSuccessful){
                            listAbsen.clear()
                            for (doc in task.result){
                                if(doc.data["tanggal"].toString().substring(3,4)==spBulan.selectedItemPosition.toString())
                                listAbsen.add(doc.toObject(Absensi::class.java))
                            }
                        }
                    }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }
}