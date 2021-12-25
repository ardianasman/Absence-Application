package com.example.proyekandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class adapternotification(
    private val listnotification : ArrayList<ClassNotification>
) : RecyclerView.Adapter<adapternotification.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _message = itemView.findViewById<TextView>(R.id.tvMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapternotification.ListViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.layoutnotification, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapternotification.ListViewHolder, position: Int) {
        var isi = listnotification[position]
        holder._message.setText(isi.message)
    }

    override fun getItemCount(): Int {
        return listnotification.size
    }

}
