package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.proyekandroid.MainActivity.Companion.data
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.editaccountdialog.*
import kotlinx.android.synthetic.main.editaccountdialog.view.*

class Account : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        navbaraccount.selectedItemId = R.id.accountnav
        navbaraccount.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.homenav -> {
                    val intacc = Intent(this@Account, Home::class.java)
                    startActivity(intacc)
                }
                R.id.notificationnav -> {
                    val intnot = Intent(this@Account, Notification::class.java)
                    startActivity(intnot)
                }

            }
            true
        }

        db.collection("user").document(data)
            .get()
            .addOnSuccessListener {
                tvNameAccount.setText(it.data?.get("name").toString())
                tvRoleAccount.setText(it.data?.get("role").toString())
                tvTelpAccount.setText(it.data?.get("telp").toString())
            }


        btnEditDialog.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.editaccountdialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Edit Profile")
                .setCancelable(false)
            val etname = mDialogView.findViewById<EditText>(R.id.etNameDialog)
            val ettelp = mDialogView.findViewById<EditText>(R.id.etTelpDialog)
            etname.setText(tvNameAccount.text.toString())
            ettelp.setText(tvTelpAccount.text.toString())
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
                        tvNameAccount.setText(it.data?.get("name").toString())
                        tvRoleAccount.setText(it.data?.get("role").toString())
                        tvTelpAccount.setText(it.data?.get("telp").toString())
                    }
            }
        }
    }
}