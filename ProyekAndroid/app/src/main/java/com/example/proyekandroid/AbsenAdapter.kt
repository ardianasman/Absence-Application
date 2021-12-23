package com.example.proyekandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AbsenAdapter (private var listAbsen: ArrayList<Absensi>) :RecyclerView.Adapter<AbsenAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.itemabsen,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNum.text= position.toString()
        holder.tvTgl.text=listAbsen[position].tanggal
        holder.tvIn.text=listAbsen[position].waktu
        holder.tvLoc.text=listAbsen[position].location
    }

    override fun getItemCount(): Int {
        return listAbsen.size
    }
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val tvNum=view.findViewById<TextView>(R.id.tvNum)
        val tvTgl=view.findViewById<TextView>(R.id.tvTgl)
        val tvIn=view.findViewById<TextView>(R.id.tvTime)
        val tvLoc=view.findViewById<TextView>(R.id.tvLoc)
    }
}