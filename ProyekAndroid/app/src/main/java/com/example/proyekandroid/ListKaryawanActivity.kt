package com.example.proyekandroid

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_pengurus.*

class ListKaryawanActivity : AppCompatActivity() {
    val listItem: ArrayList<Karyawan> = ArrayList()
    lateinit var adapter: ListKaryawanAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_karyawan)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        adapter = ListKaryawanAdapter(listItem)
        adapter.setOnItemClickListener(object: ListKaryawanAdapter.OnItemClickListener {
            override fun showDetail(position: Int, dataAdapter: ListKaryawanAdapter) {
                val k = listItem[position]
                val b = Bundle()
                b.putString(InputKaryawanActivity.KEY_USERNAME, k.email)
                b.putString(InputKaryawanActivity.KEY_PASSWORD, k.password)
                b.putString(InputKaryawanActivity.KEY_NAME, k.name)
                b.putString(InputKaryawanActivity.KEY_PHONE, k.telp)
                b.putInt(InputKaryawanActivity.KEY_MODE, 0)
                val intent = Intent(this@ListKaryawanActivity, PreviewActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }

        })
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        readData(db)

        val navbarPengurus = findViewById<BottomNavigationView>(R.id.navbarPengurus)
        navbarPengurus.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.inputnav -> {
                    val intnot = Intent(this@ListKaryawanActivity, InputKaryawanActivity::class.java)
                    startActivity(intnot)
                }
                R.id.homenav -> {
                    val intnot = Intent(this@ListKaryawanActivity, HomePengurusActivity::class.java)
                    startActivity(intnot)
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun readData(db: FirebaseFirestore) {
        db.collection("user").get()
            .addOnSuccessListener { result ->
                listItem.clear()
                for (doc in result) {
                    if (doc.data.get("role").toString() == "Karyawan") {
                        val n = Karyawan(
                            doc.get("check_in").toString().toBoolean(),
                            doc.data.get("email").toString(),
                            doc.data.get("name").toString(),
                            doc.data.get("password").toString(),
                            doc.data.get("pic").toString(),
                            doc.data.get("role").toString(),
                            doc.data.get("telp").toString()
                        )
                        listItem.add(n)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this@ListKaryawanActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}