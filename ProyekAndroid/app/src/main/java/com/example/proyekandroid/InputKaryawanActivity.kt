package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.imageview.ShapeableImageView

class InputKaryawanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_karyawan)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val ivPhoto = findViewById<ShapeableImageView>(R.id.ivPhoto)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnPreview = findViewById<Button>(R.id.btnPreview)
        btnPreview.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            var canContinue = true
            var errorMsg = ""
            if (username.isEmpty()) {
                canContinue = false
                errorMsg = "Please fill the username!\n"
            }
            if (password.isEmpty()) {
                canContinue = false
                errorMsg += "Please fill the password!\n"
            }
            if (name.isEmpty()) {
                canContinue = false
                errorMsg += "Please fill your name!\n"
            }
            if (phone.isEmpty()) {
                canContinue = false
                errorMsg += "Plase fill your phone number!\n"
            }
            if (canContinue) {
                val b = Bundle()
                b.putString(KEY_USERNAME, username)
                b.putString(KEY_PASSWORD, password)
                b.putString(KEY_NAME, name)
                b.putString(KEY_PHONE, phone)
                val intent = Intent(this@InputKaryawanActivity, PreviewActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_PASSWORD = "password"
        const val KEY_NAME = "name"
        const val KEY_PHONE = "phone"
    }
}