package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home_pengurus.*

class HomePengurusActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_pengurus)

        navbarPengurus.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.inputnav -> {
                    val intnot = Intent(this@HomePengurusActivity, InputKaryawanActivity::class.java)
                    startActivity(intnot)
                }
                R.id.calendarnav -> {
                    val intcal = Intent(this@HomePengurusActivity, CalendarActivity::class.java)
                    startActivity(intcal)
                }
                R.id.accountnav -> {
                    val intacc = Intent(this@HomePengurusActivity, AccountPengurusActivity::class.java)
                    startActivity(intacc)
                }
            }
            true
        }

        ivKaryawan.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, ListKaryawanActivity::class.java)
            startActivity(intent)
        }
        ivGaji.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, SetGajiBackup::class.java)
            startActivity(intent)
        }
        ivLaporan.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, LaporanAbsenActivity::class.java)
            startActivity(intent)
        }
        ivQr.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, QRCodeActivity::class.java)
            startActivity(intent)
        }
        ivCuti.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, AcceptCuti::class.java)
            startActivity(intent)
        }
        ivIzin.setOnClickListener {
            val intent = Intent(this@HomePengurusActivity, AcceptIzin::class.java)
            startActivity(intent)
        }
    }
}