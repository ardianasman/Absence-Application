package com.example.proyekandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.proyekandroid.MainActivity.Companion.data
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.dialog_edit_password.*
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
                R.id.calnav -> {
                    val intent = Intent(this@Account, CalendarKaryawan::class.java)
                    startActivity(intent)
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
        btnEditPassword.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogview = LayoutInflater.from(this@Account).inflate(R.layout.dialog_edit_password, null, false)
            val _etPasswordEditPassword = dialogview.findViewById<EditText>(R.id.etPasswordEditPassword)
            val _etNewPasswordEditPassword = dialogview.findViewById<EditText>(R.id.etNewPasswordEditPassword)
            builder.setPositiveButton("Save"){ dialog, _ ->
                db.collection("user").document(data)
                    .get()
                    .addOnSuccessListener { result ->
                        if(result.data?.get("password").toString() != _etPasswordEditPassword.text.toString()){
                            Toast.makeText(this, "Wrong current password",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            if(_etNewPasswordEditPassword.text.toString() == ""){
                                Toast.makeText(this, "Fill new password",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                db.collection("user").document(data)
                                    .update("password", _etNewPasswordEditPassword.text.toString())
                                Toast.makeText(this, "Password change successfully",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel"){ dialog, _ ->
                dialog.dismiss()
            }

            builder.setView(dialogview)
            builder.create()
            builder.show()
        }
        btnLogout.setOnClickListener {
            val intent = Intent(this@Account, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}