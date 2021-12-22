package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navbarhome.setOnItemSelectedListener { menu ->
            when(menu.itemId){
                R.id.notificationnav -> {
                    val intnot = Intent(this@Home, Notification::class.java)
                    startActivity(intnot)
                }
                R.id.accountnav -> {
                    val intacc = Intent(this@Home, Account::class.java)
                    startActivity(intacc)
                }
            }
            true
        }

        ivScan.setOnClickListener {

            val intscan = Intent(this@Home, ScanActivity::class.java)
            startActivityForResult(intscan, SCAN_REQUEST_CODE)
        }
        ivIzin.setOnClickListener {
            val intscan = Intent(this@Home, Izin::class.java)
            startActivity(intscan)
        }
        ivCuti.setOnClickListener {
            val intscan = Intent(this@Home, Cuti::class.java)
            startActivity(intscan)
        }

        ivLaporan.setOnClickListener {
            val intscan = Intent(this@Home, ScanActivity::class.java)
            startActivity(intscan)
        }
        ivGaji.setOnClickListener {
            val intscan = Intent(this@Home, ScanActivity::class.java)
            startActivity(intscan)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                var myLocation = ""
                var tgl = ""
                var waktu = ""
                var username = ""
                var isCheckIn = false
                data?.getStringExtra(ScanActivity.KEY_LOCATION).let {
                    if (it != null) {
                        myLocation = it
                    }
                }
                data?.getStringExtra(ScanActivity.KEY_TGL).let {
                    if (it != null) {
                        tgl = it
                    }
                }
                data?.getStringExtra(ScanActivity.KEY_WAKTU).let {
                    if (it != null) {
                        waktu = it
                    }
                }
                val bundle = intent.extras
                if (bundle != null) {
                    username = bundle.getString(MainActivity.KEY_USERNAME).toString()
                }

                //get check_in status
                db.collection("user").document(username).get()
                    .addOnSuccessListener { doc ->
                        if (doc != null) {
                            isCheckIn = doc.get("check_in").toString().toBoolean()
                            val newStatus = isCheckIn.not()
                            val absensi = Absensi(newStatus, myLocation, tgl, username, waktu)
                            val db = FirebaseFirestore.getInstance()
                            db.collection("absensi").document(tgl + "_" + waktu)
                                .set(absensi)
                            db.collection("user")
                                .document(username).update("check_in", newStatus)
                            Toast.makeText(this@Home, "Scan succeeded!", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    companion object{
        const val SCAN_REQUEST_CODE = 1199
    }

}


