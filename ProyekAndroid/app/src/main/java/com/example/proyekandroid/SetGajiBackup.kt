package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_set_gaji_backup.*

private var arKaryawan = ArrayList<String>()

class SetGajiBackup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_gaji_backup)

        getKaryawan()

        val adapterk = ArrayAdapter(this, R.layout.dropdownlist, arKaryawan)
        dropk.setAdapter(adapterk)
        var selectedtext = ""
        dropk.doOnTextChanged { text, start, before, count ->
            selectedtext = text.toString()
        }

        btnSaveSetGaji.setOnClickListener {
            if(etSetGajiPokok.text.toString() == ""){
                Toast.makeText(this, "Please fill Gaji Pokok", Toast.LENGTH_SHORT).show()
            }
            else{
                var cek : Int = 0
                db.collection("gaji")
                    .get()
                    .addOnSuccessListener {
                        if(it.isEmpty == true){
                            db.collection("gaji").document(selectedtext)
                                .set(ClassGaji(etSetGajiPokok.text.toString(),etSetGajiLembur.text.toString(),etSetGajiBonus.text.toString(),etSetGajiDenda.text.toString(),selectedtext))
                            val intent = Intent(this@SetGajiBackup, HomePengurusActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            for(docs in it){
                                if(docs.data.get("username") == selectedtext){
                                    db.collection("gaji").document(selectedtext)
                                        .update("pokok", etSetGajiPokok.text.toString(),
                                        "lembur",etSetGajiLembur.text.toString(),
                                        "bonus",etSetGajiBonus.text.toString(),
                                        "denda",etSetGajiDenda.text.toString())
                                }
                            }
                            val intent = Intent(this@SetGajiBackup, HomePengurusActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

            }
        }
    }

    private fun getKaryawan(){
        db.collection("user")
            .get()
            .addOnSuccessListener { result ->
                arKaryawan.clear()
                for(docs in result){
                    if(docs.data.get("role") == "Karyawan"){

                        var data = docs.data.get("email").toString()
                        arKaryawan.add(data)
                    }
                }
            }
    }
}