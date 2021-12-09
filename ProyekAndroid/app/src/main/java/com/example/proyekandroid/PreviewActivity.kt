package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val ivPhoto = findViewById<ShapeableImageView>(R.id.ivPhoto)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val bundle = intent.extras
        etUsername.setText(bundle?.getString(InputKaryawanActivity.KEY_USERNAME))
        etName.setText(bundle?.getString(InputKaryawanActivity.KEY_NAME))
        etPhone.setText(bundle?.getString(InputKaryawanActivity.KEY_PHONE))
        etUsername.isEnabled = false
        etName.isEnabled = false
        etPhone.isEnabled = false
        btnAdd.setOnClickListener {
            val intent = Intent(this@PreviewActivity, ListKaryawanActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}