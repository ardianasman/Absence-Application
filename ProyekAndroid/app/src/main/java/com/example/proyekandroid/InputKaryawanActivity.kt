package com.example.proyekandroid

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_home_pengurus.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class InputKaryawanActivity : AppCompatActivity() {
    private val REQ_CODE = 705
    private val REQUEST_IMAGE_CAPTURE = 12
    private lateinit var ivPhoto: ImageView
    private lateinit var currentPhotoPath: String
    private val permissions:Array<String?> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_karyawan)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        ivPhoto = findViewById(R.id.ivPhoto)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnPreview = findViewById<Button>(R.id.btnPreview)
        val navbarPengurus = findViewById<BottomNavigationView>(R.id.navbarPengurus)
        currentPhotoPath = ""
        ivPhoto.setOnClickListener {
            if (checkPermissions(permissions)) {
                launchCamera()
            } else {
                handlePermissions(permissions)
            }
        }
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
                errorMsg += "Please fill your phone number!\n"
            }
//            if (currentPhotoPath.isEmpty()) {
//                canContinue = false
//                errorMsg += "Please fill profile picture!\n"
//            }
            if (canContinue) {
                val b = Bundle()
                b.putString(KEY_USERNAME, username)
                b.putString(KEY_PASSWORD, password)
                b.putString(KEY_NAME, name)
                b.putString(KEY_PHONE, phone)
                b.putString(KEY_PIC, currentPhotoPath)
                b.putInt(KEY_MODE, 1)
                val intent = Intent(this@InputKaryawanActivity, PreviewActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }
        }

        navbaraccount.selectedItemId = R.id.inputnav
        navbarPengurus.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homenav -> {
                    val intnot = Intent(this@InputKaryawanActivity, HomePengurusActivity::class.java)
                    startActivity(intnot)
                }
                R.id.calendarnav -> {
                    val intcal = Intent(this@InputKaryawanActivity, CalendarActivity::class.java)
                    startActivity(intcal)
                }
                R.id.accountnav -> {
                    val intacc = Intent(this@InputKaryawanActivity, AccountPengurusActivity::class.java)
                    startActivity(intacc)
                }
            }
            true
        }
    }

    private fun launchCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.proyekandroid",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    try {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this@InputKaryawanActivity, e.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Glide.with(this@InputKaryawanActivity)
                .load(Uri.fromFile(File(currentPhotoPath)))
                .circleCrop()
                .into(ivPhoto)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun handlePermissions(permissions: Array<String?>) {
        var permissions: Array<String?>? = permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notGrantedPerms: ArrayList<String?> = ArrayList()
            for (p in permissions!!) {
                if (checkSelfPermission(p!!) != PackageManager.PERMISSION_GRANTED) notGrantedPerms.add(
                    p
                )
            }
            permissions = notGrantedPerms.toArray(arrayOfNulls(0))
            if (permissions != null && permissions.isNotEmpty()) requestPermissions(permissions, REQ_CODE)
        }
    }

    private fun checkPermissions(permissions: Array<String?>): Boolean {
        var permissions: Array<String?>? = permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notGrantedPerms: ArrayList<String?> = ArrayList()
            for (p in permissions!!) {
                if (checkSelfPermission(p!!) != PackageManager.PERMISSION_GRANTED) notGrantedPerms.add(
                    p
                )
            }
            permissions = notGrantedPerms.toArray(arrayOfNulls(0))
            if (permissions != null && permissions.isNotEmpty()) return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera()
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
        const val KEY_PIC = "pic"
        const val KEY_MODE = "mode"
    }
}