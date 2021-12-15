package com.example.proyekandroid

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnLogin
import kotlinx.android.synthetic.main.activity_main.root_lay
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val animDrawable = root_lay.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()


        val btnShake : Animation = AnimationUtils.loadAnimation(this,R.anim.buttonshake)

        btnRegister.setOnClickListener{
            if(tiEmailRegister.text.toString() == "" || tiPasswordRegister.text.toString() == ""){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                btnRegister.startAnimation(btnShake)
            }
            else{
                db.collection("user").document(tiEmailRegister.text.toString())
                    .set(User(tiEmailRegister.text.toString(), tiPasswordRegister.text.toString()))
                    .addOnSuccessListener {
                        val intregis= Intent(this@Register, MainActivity::class.java)
                        startActivity(intregis)
                    }
            }
        }
    }
}