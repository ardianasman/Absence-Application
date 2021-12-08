package com.example.proyekandroid

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.imageview.ShapeableImageView

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val ivPhoto = findViewById<ShapeableImageView>(R.id.ivPhoto)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        etUsername.isEnabled = false
        etName.isEnabled = false
        etPhone.isEnabled = false
        btnAdd.setOnClickListener {

        }
    }
}