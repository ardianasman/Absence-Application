package com.example.proyekandroid

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.root_lay
import kotlinx.android.synthetic.main.activity_register.*

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val animDrawable = root_lay.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()

        val btnShake : Animation = AnimationUtils.loadAnimation(this,R.anim.buttonshake)

        btnSaveForgotPassword.setOnClickListener {
            if(tiEmailForgot.text.toString() == "" || tiPasswordForgot.text.toString() == "" || tiConfirmPasswordForgot.text.toString() == ""){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                btnSaveForgotPassword.startAnimation(btnShake)
            }
            else{
                if(tiPasswordForgot.text.toString() == tiConfirmPasswordForgot.text.toString()){
                    db.collection("user").document(tiEmailForgot.text.toString())
                        .set(User(tiEmailForgot.text.toString(), tiPasswordForgot.text.toString()))
                        .addOnSuccessListener {
                            val intforgot= Intent(this@ForgotPassword, MainActivity::class.java)
                            startActivity(intforgot)
                        }
                }
                else{
                    Toast.makeText(this, "Different confirm password", Toast.LENGTH_LONG).show()
                    btnSaveForgotPassword.startAnimation(btnShake)
                }
            }
        }
    }
}