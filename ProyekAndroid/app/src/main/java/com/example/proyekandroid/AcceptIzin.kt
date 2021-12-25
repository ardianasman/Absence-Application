package com.example.proyekandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_accept_izin.*

private var arPengajuanIzin = arrayListOf<ClassCuti>()

class AcceptIzin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_izin)

        getPengajuan()
    }

    private fun getPengajuan(){
        db.collection("izin")
            .get()
            .addOnSuccessListener { result ->
                arPengajuanIzin.clear()
                for(docs in result){
                    if(docs.data.get("status") == "0"){
                        var data = ClassCuti(
                            docs.data.get("durasi").toString() + " Days",
                            docs.data.get("keterangan").toString(),
                            docs.data.get("start").toString(),
                            docs.data.get("end").toString(),
                            docs.data.get("username").toString(),
                            docs.data.get("status").toString()
                        )
                        arPengajuanIzin.add(data)
                    }
                }
                Tampilkan()
            }
    }

    private fun Tampilkan() {
        val adapterP = adapterpengajuan(arPengajuanIzin)
        rvPengajuanIzin.layoutManager = LinearLayoutManager(this)
        rvPengajuanIzin.adapter = adapterP

        adapterP.setOnItemClickCallback(object : adapterpengajuan.OnItemClickCallback {
            override fun onItemClickedAccept(datax : ClassCuti){
                Accept(datax)
            }

            override fun onItemClickedDenie(datax: ClassCuti) {
                Denie(datax)
            }
        })
    }
    private fun Accept(datax: ClassCuti) {
        db.collection("izin")
            .get()
            .addOnSuccessListener { result ->
                for (docs in result){
                    if(docs.data.get("username") == datax.username.toString() && docs.data.get("end") == datax.end.toString()){

                        db.collection("izin").document(docs.id.toString())
                            .update("status", "1")
                        db.collection("notification").document()
                            .set(ClassNotification("Your permission application has been approved", docs.data.get("username").toString()))
                    }
                }
                getPengajuan()
            }
    }
    private fun Denie(datax: ClassCuti) {
        db.collection("izin")
            .get()
            .addOnSuccessListener { result ->
                for (docs in result){
                    if(docs.data.get("username") == datax.username.toString() && docs.data.get("end") == datax.end.toString()){

                        db.collection("izin").document(docs.id.toString())
                            .update("status", "2")
                        db.collection("notification").document()
                            .set(ClassNotification("Your permission application has been denied", docs.data.get("username").toString()))
                    }
                }
                getPengajuan()
            }
    }
}