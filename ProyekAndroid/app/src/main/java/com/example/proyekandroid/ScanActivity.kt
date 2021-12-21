package com.example.proyekandroid

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import java.text.SimpleDateFormat
import java.util.*

import android.content.Intent
import android.content.SharedPreferences
import android.location.Geocoder
import android.location.Location
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore


class ScanActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var scannerView: CodeScannerView
    private val CAMERA_REQ_CODE = 1010
    private val GPS_REQ_CODE = 1011
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var myLocation:String

    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val IS_CHECK_IN = "ischeckin"

    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        scannerView = findViewById(R.id.scanner_view)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        db = FirebaseFirestore.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        sharedPreference = getSharedPreferences("com.kawoo.nitnit.recyclerview", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        val isGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                CAMERA_REQ_CODE
            )
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    val geocoder = Geocoder(this@ScanActivity, Locale.getDefault())
                    val addresses = location?.longitude?.let {
                        location?.latitude?.let { it1 ->
                            geocoder.getFromLocation(
                                it1,
                                it, 1)
                        }
                    }
                    myLocation = if (!addresses.isNullOrEmpty()) {
                        addresses[0].getAddressLine(0) + ", " + addresses[0].locality + ", " +
                                addresses[0].adminArea + ", " + addresses[0].countryName
                    } else ""
                }
            initScanner()
        }
    }

    private fun initScanner() {
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else {
                    val tgl: String = SimpleDateFormat("dd-MM-yyyy").format(Date())
                    val waktu: String = SimpleDateFormat("HH:mm:ss").format(Date())
                    var isCheckIn = true
                    if (sharedPreference.contains(IS_CHECK_IN)) {
                        isCheckIn = !sharedPreference.getBoolean(IS_CHECK_IN, true)
                        editor.putBoolean(IS_CHECK_IN, isCheckIn)
                    } else {
                        editor.putBoolean(IS_CHECK_IN, true)
                    }

                    val absensi = Absensi(isCheckIn, myLocation, tgl, "agung@gmail.com", waktu)
                    db.collection("absensi").document(tgl + "_" + waktu)
                        .set(absensi)
                        .addOnSuccessListener {
                            setResult(RESULT_OK)
                            finish()
                        }
                        .addOnFailureListener {
                            setResult(Home.SCAN_RESULT_FAILED)
                            finish()
                        }
                }

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes"
            ) { _, _ -> startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_REQ_CODE) }
            .setNegativeButton("No"
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initScanner()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GPS_REQ_CODE && resultCode == RESULT_OK) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    CAMERA_REQ_CODE
                )
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    val geocoder = Geocoder(this@ScanActivity, Locale.getDefault())
                    val addresses = location?.longitude?.let {
                        location?.latitude?.let { it1 ->
                            geocoder.getFromLocation(
                                it1,
                                it, 1)
                        }
                    }
                    myLocation = if (!addresses.isNullOrEmpty()) {
                        addresses[0].getAddressLine(0) + ", " + addresses[0].locality + ", " +
                                addresses[0].adminArea + ", " + addresses[0].countryName
                    } else ""
                }
        }
    }

    override fun onResume() {
        super.onResume()
        val isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (isGranted) codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        val isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (isGranted) codeScanner.startPreview()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }
}