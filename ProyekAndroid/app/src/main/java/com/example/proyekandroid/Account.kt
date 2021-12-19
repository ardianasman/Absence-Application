package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_notification.*

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        navbaraccount.selectedItemId = R.id.accountnav
        navbaraccount.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.homenav -> {
                    val intacc = Intent(this@Account, Home::class.java)
                    startActivity(intacc)
                }
                R.id.notificationnav -> {
                    val intnot = Intent(this@Account, Notification::class.java)
                    startActivity(intnot)
                }

            }
            true
        }
    }
}