package com.example.proyekandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

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
        var pic = ""
        if (bundle != null) {
            pic = bundle.getString(InputKaryawanActivity.KEY_PIC).toString()
        }
        Glide.with(this)
            .load(Uri.fromFile(File(pic)))
            .circleCrop()
            .into(ivPhoto)
        etUsername.isEnabled = false
        etPassword.isEnabled = false
        etName.isEnabled = false
        etPhone.isEnabled = false
        if (bundle?.getInt(InputKaryawanActivity.KEY_MODE) == 0) {
            btnAdd.visibility = View.GONE
        }
        btnAdd.setOnClickListener {
            val username = etUsername.text.toString()
            val pwd = etPassword.text.toString()
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val k = Karyawan(username, name, pwd, pic, "Karyawan", phone)
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