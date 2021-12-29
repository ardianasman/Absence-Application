package com.example.proyekandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.se.omapi.Session
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isInvisible
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

lateinit var db : FirebaseFirestore

class MainActivity : AppCompatActivity() {
    companion object{
        var data : String =""
        const val KEY_USERNAME = "username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animDrawable = root_lay.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()


        db = FirebaseFirestore.getInstance()


        val btnShake : Animation = AnimationUtils.loadAnimation(this,R.anim.buttonshake)



        btntoRegister.setOnClickListener{
            val intregis = Intent(this@MainActivity, Register::class.java)
            startActivity(intregis)
        }
        btnForgotPassword.setOnClickListener{
            val intforgot = Intent(this@MainActivity, ForgotPassword::class.java)
            startActivity(intforgot)
        }
        btnLogin.setOnClickListener{
            db.collection("user")
                .get()
                .addOnSuccessListener { result ->
                    for(docs in result){
                        if(docs.data.get("email") == tiEmailLogin.text.toString()){
                            if(docs.data.get("password") == tiPasswordLogin.text.toString()){
                                val bundle = Bundle()
                                if(docs.data.get("role") == "Karyawan"){
                                    val intlogin = Intent(this@MainActivity, Home::class.java)
                                    bundle.putString(KEY_USERNAME, tiEmailLogin.text.toString())
                                    intlogin.putExtras(bundle)
                                    startActivity(intlogin)
                                }
                                else if (docs.data.get("role") == "Pengurus"){
                                    val intlogin = Intent(this@MainActivity, HomePengurusActivity::class.java)
                                    startActivity(intlogin)
                                }
                                data = tiEmailLogin.text.toString()
                            }
                            else {
                                btnLogin.startAnimation(btnShake)
                                Toast.makeText(this, "Wrong login", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
        }
        btntoRegister.isInvisible = true
    }
}