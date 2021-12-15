package com.example.proyekandroid

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

lateinit var db : FirebaseFirestore

class MainActivity : AppCompatActivity() {
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
                                val intlogin = Intent(this@MainActivity, Home::class.java)
                                startActivity(intlogin)
                            }
                            else {
                                btnLogin.startAnimation(btnShake)
                                Toast.makeText(this, "Wrong login", Toast.LENGTH_LONG).show()
                            }
                        }
                        else {
                            btnLogin.startAnimation(btnShake)
                            Toast.makeText(this, "Wrong login", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
}