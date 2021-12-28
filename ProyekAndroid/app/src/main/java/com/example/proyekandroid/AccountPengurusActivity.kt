package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyekandroid.MainActivity.Companion.data
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_account_pengurus.*
import kotlinx.android.synthetic.main.editaccountdialog.view.*

class AccountPengurusActivity : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_pengurus)
        navBarAccPengurus.selectedItemId=R.id.accountnav
        navBarAccPengurus.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homenav -> {
                    val inthome = Intent(this@AccountPengurusActivity, HomePengurusActivity::class.java)
                    startActivity(inthome)
                }
                R.id.inputnav -> {
                    val intnot = Intent(this@AccountPengurusActivity, InputKaryawanActivity::class.java)
                    startActivity(intnot)
                }
                R.id.calendarnav -> {
                    val intcal = Intent(this@AccountPengurusActivity, CalendarActivity::class.java)
                    startActivity(intcal)
                }
            }
            true
        }
        db= FirebaseFirestore.getInstance()
        db.collection("user").document(data).get()
            .addOnSuccessListener {
                tvNamePengurus.text = it.data?.get("name").toString()
                tvRolePengurus.text=  it.data?.get("role").toString()
                tvTelpPengurus.text = it.data?.get("telp").toString()
            }
        btnEditPengurus.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.editaccountdialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Edit Profile")
                .setCancelable(false)
            val etname = mDialogView.findViewById<EditText>(R.id.etNameDialog)
            val ettelp = mDialogView.findViewById<EditText>(R.id.etTelpDialog)
            etname.setText(tvNamePengurus.text.toString())
            ettelp.setText(tvTelpPengurus.text.toString())
            val mAlertDialog = mBuilder.show()

            mDialogView.btnCancelDialog.setOnClickListener {
                mAlertDialog.dismiss()
            }
            mDialogView.btnConfirmDialog.setOnClickListener {
                db.collection("user").document(data)
                    .update("name", etname.text.toString(),"telp", ettelp.text.toString())
                mAlertDialog.dismiss()
                db.collection("user").document(data)
                    .get()
                    .addOnSuccessListener {
                        tvNamePengurus.text = it.data?.get("name").toString()
                        tvRolePengurus.text=  it.data?.get("role").toString()
                        tvTelpPengurus.text = it.data?.get("telp").toString()
                    }
            }
        }
    }
}