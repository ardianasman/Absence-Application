package com.example.proyekandroid

import android.content.Intent
import android.graphics.ColorSpace
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navbarhome.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.notificationnav -> {
                    val intnot = Intent(this@Home, Notification::class.java)
                    startActivity(intnot)
                }
                R.id.accountnav -> {
                    val intacc = Intent(this@Home, Account::class.java)
                    startActivity(intacc)
                }
            }
            true
        }

        ivScan.setOnClickListener {
            val intscan = Intent(this@Home, ScanActivity::class.java)
            startActivity(intscan)
        }
        ivIzin.setOnClickListener {
            val intscan = Intent(this@Home, ScanActivity::class.java)
            startActivity(intscan)
        }
        ivCuti.setOnClickListener {
            val intscan = Intent(this@Home, Cuti::class.java)
            startActivity(intscan)
        }

        ivLaporan.setOnClickListener {
            val intscan = Intent(this@Home, ScanActivity::class.java)
            startActivity(intscan)
        }
        ivGaji.setOnClickListener {
            val intscan = Intent(this@Home, ScanActivity::class.java)
            startActivity(intscan)
        }
    }

}


