package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyekandroid.MainActivity.Companion.data
import kotlinx.android.synthetic.main.activity_see_gaji_backup.*
import java.text.DecimalFormat
import java.text.NumberFormat

class SeeGajiBackup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_gaji_backup)

        val snf : NumberFormat =DecimalFormat("#,###")
        var formattednumber : String



        db.collection("gaji")
            .get()
            .addOnSuccessListener { result ->
                for(docs in result){
                    if(data == docs.data.get("username").toString()){
                        formattednumber = snf.format(docs.data.get("pokok").toString().toDouble())
                        tvSeePokok.setText(formattednumber)
                        formattednumber = snf.format(docs.data.get("lembur").toString().toDouble())
                        tvSeeLembur.setText(formattednumber)
                        formattednumber = snf.format(docs.data.get("bonus").toString().toDouble())
                        tvSeeBonus.setText(formattednumber)
                        formattednumber = snf.format(docs.data.get("denda").toString().toDouble())
                        tvSeeDenda.setText(formattednumber)

                        val total : Double = (docs.data.get("pokok").toString().toDouble() + docs.data.get("lembur").toString().toDouble()
                        + docs.data.get("bonus").toString().toDouble() - docs.data.get("denda").toString().toDouble())
                        formattednumber = snf.format(total)
                        tvSeeTotal.setText(formattednumber)
                        break
                    }
                }
            }
    }
}