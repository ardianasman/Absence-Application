package com.example.proyekandroid

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class adapterutama(
    private val listcuti : ArrayList<ClassCuti>
) : RecyclerView.Adapter<adapterutama.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _durasi : TextView = itemView.findViewById(R.id.tvLeaveDur)
        var _keterangan : TextView = itemView.findViewById(R.id.tvKeterangan)
        var _start : TextView = itemView.findViewById(R.id.tvLeaveDate)
        var _status : TextView = itemView.findViewById(R.id.tvLeaveStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterutama.ListViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.layoutizincuti, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterutama.ListViewHolder, position: Int) {
        var isi = listcuti[position]
        holder._durasi.setText(isi.durasi)
        holder._keterangan.setText(isi.keterangan)
        holder._start.setText(isi.start)

        if(isi.status == "0"){
            holder._status.setText("Pending")
            holder._status.setBackgroundColor(Color.parseColor("#caf0f8"))
        }
        else if(isi.status == "1"){
            holder._status.setText("Approved")
            holder._status.setBackgroundColor(Color.parseColor("#24c200"))
        }
        else if(isi.status == "2"){
            holder._status.setText("Denied")
            holder._status.setBackgroundColor(Color.parseColor("#c20000"))
        }
    }

    override fun getItemCount(): Int {
        return listcuti.size
    }

}
