package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekandroid.MainActivity.Companion.data
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_notification.*

private var arNotification = arrayListOf<ClassNotification>()

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
        getNotification()
    }
    private fun getNotification(){
        db.collection("notification")
            .get()
            .addOnSuccessListener { result ->
                arNotification.clear()
                for(docs in result){
                    println(docs.data.get("message"))
                    if(docs.data.get("username") == data){
                        var data = ClassNotification(
                            docs.data.get("message").toString(),
                            docs.data.get("username").toString()
                        )
                        arNotification.add(data)
                    }
                }
                Tampilkan()
            }
    }
    private fun Tampilkan(){
        val adapterN = adapternotification(arNotification)
        rvNotification.layoutManager = LinearLayoutManager(this)
        rvNotification.adapter = adapterN
        println(arNotification)
    }
}