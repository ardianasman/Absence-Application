package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_notification.*

class Notification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)


        navbarnotifications.selectedItemId = R.id.notificationnav
        navbarnotifications.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.homenav -> {
                    val intacc = Intent(this@Notification, Home::class.java)
                    startActivity(intacc)
                }
                R.id.accountnav -> {
                    val intacc = Intent(this@Notification, Account::class.java)
                    startActivity(intacc)
                }

            }
            true
        }
    }

}