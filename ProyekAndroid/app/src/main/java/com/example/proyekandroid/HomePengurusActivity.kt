package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home_pengurus.*

class HomePengurusActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pengurus)

        ivKaryawan.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, ListKaryawanActivity::class.java)
            startActivity(intent)
        }

        ivQr.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, QRCodeActivity::class.java)
            startActivity(intent)
        }
    }
}