package com.example.proyekandroid

import android.content.Intent
import android.graphics.ColorSpace
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

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
    }

}


