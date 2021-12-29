package com.example.proyekandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class adaptercalendar(
    private val listcalendar : ArrayList<Pengumuman>
) : RecyclerView.Adapter<adaptercalendar.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _judul = itemView.findViewById<TextView>(R.id.tvJudulCalendar)
    }

    override fun onCreateViewHolder( parent: ViewGroup,  viewType: Int): adaptercalendar.ListViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.layoutevents, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adaptercalendar.ListViewHolder, position: Int) {
        var isi = listcalendar[position]
        holder._judul.setText(isi.judul)
    }

    override fun getItemCount(): Int {
        return listcalendar.size
    }

}
