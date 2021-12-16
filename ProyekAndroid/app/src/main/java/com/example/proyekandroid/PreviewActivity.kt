package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.firestore.FirebaseFirestore

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val ivPhoto = findViewById<ShapeableImageView>(R.id.ivPhoto)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnList = findViewById<Button>(R.id.btnList)
        val bundle = intent.extras
        etUsername.setText(bundle?.getString(InputKaryawanActivity.KEY_USERNAME))
        etPassword.setText(bundle?.getString(InputKaryawanActivity.KEY_PASSWORD))
        etName.setText(bundle?.getString(InputKaryawanActivity.KEY_NAME))
        etPhone.setText(bundle?.getString(InputKaryawanActivity.KEY_PHONE))
        etUsername.isEnabled = false
        etPassword.isEnabled = false
        etName.isEnabled = false
        etPhone.isEnabled = false
        btnAdd.setOnClickListener {
            val username = etUsername.text.toString()
            val pwd = etPassword.text.toString()
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val k = Karyawan(username, name, pwd, phone)
            db.collection("user").document(username)
                .set(k)
                .addOnSuccessListener {
                    Toast.makeText(this@PreviewActivity, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@PreviewActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
        btnList.setOnClickListener {
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