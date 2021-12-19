package com.example.proyekandroid

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

        val role : ArrayList<String> = arrayListOf("Karyawan", "Pengurus")
        val adapterdrop = ArrayAdapter(this, R.layout.dropdownlist,role)
        adapterdrop.setDropDownViewResource(R.layout.dropdownlist)
        spinnerrole.adapter = adapterdrop

        var selectedrole : String = ""
        spinnerrole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>, p1: View?, p2: Int, p3: Long) {
                selectedrole = p0.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        btnRegister.setOnClickListener{
            if(tiEmailRegister.text.toString() == "" || tiPasswordRegister.text.toString() == ""){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                btnRegister.startAnimation(btnShake)
            }
            else{
                db.collection("user").document(tiEmailRegister.text.toString())
                    .set(User(tiEmailRegister.text.toString(), tiEmailRegister.text.toString(), tiPasswordRegister.text.toString(), selectedrole.toString(), ""))
                val intregis= Intent(this@Register, MainActivity::class.java)
                startActivity(intregis)
            }
        }
    }
}