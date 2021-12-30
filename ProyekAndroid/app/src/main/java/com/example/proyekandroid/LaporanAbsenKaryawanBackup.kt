package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyekandroid.MainActivity.Companion.data
import kotlinx.android.synthetic.main.activity_laporan_absen_karyawan_backup.*

private var arLaporan = arrayListOf<Absensi>()

class LaporanAbsenKaryawanBackup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_absen_karyawan_backup)

        getLaporan()
    }

    private fun getLaporan(){
        db.collection("absensi")
            .get()
            .addOnSuccessListener { result ->
                arLaporan.clear()
                for(docs in result){
                    if(docs.data.get("username") == data){
                        var datax = Absensi(
                            docs.data.get("check_in").toString().toBoolean(),
                            docs.data.get("location").toString(),
                            docs.data.get("tanggal").toString(),
                            docs.data.get("username").toString(),
                            docs.data.get("waktu").toString(),
                        )
                        arLaporan.add(datax)
                    }
                }
                Tampilkan()
            }
    }
    private fun Tampilkan(){
        val adapterL = adapterLaporanabsen(arLaporan)
        rvLaporanAbsenKaryawan.layoutManager = LinearLayoutManager(this)
        rvLaporanAbsenKaryawan.adapter = adapterL
    }
}