package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.proyekandroid.MainActivity.Companion.KEY_USERNAME
import com.google.firebase.firestore.FirebaseFirestore

class SeeGajiKaryawan : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_gaji_karyawan)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        db= FirebaseFirestore.getInstance()
        val tvPokok:TextView=findViewById(R.id.tvPokok)
        val tvLembur:TextView=findViewById(R.id.tvLembur)
        val tvBonus:TextView=findViewById(R.id.tvBonus)
        val tvDenda:TextView=findViewById(R.id.tvDenda)
        val tvTotal:TextView=findViewById(R.id.tvTotal)
        var user = null
        db.collection("gaji").whereEqualTo("username", KEY_USERNAME).get().addOnCompleteListener {            task->
            if (task.isSuccessful){
                for (doc in task.result){
                    var pokok=doc.data["gaji_pokok"].toString().toInt()
                    var lembur=doc.data["lembur"].toString().toInt()
                    var bonus=doc.data["bonus"].toString().toInt()
                    var denda=doc.data["denda"].toString().toInt()
                    var total=pokok+lembur+bonus-denda
                    tvPokok.text= "Rp $pokok"
                    tvLembur.text= "Rp $lembur"
                    tvBonus.text= "Rp $bonus"
                    tvDenda.text= "Rp $denda"
                    tvTotal.text= "Rp $total"
                }
            }
        }

    }
}