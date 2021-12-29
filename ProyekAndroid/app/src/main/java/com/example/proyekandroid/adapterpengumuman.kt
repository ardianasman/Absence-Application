package com.example.proyekandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class adapterpengumuman(
    private val listpengumuman : ArrayList<Pengumuman>
) : RecyclerView.Adapter<adapterpengumuman.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _judul = itemView.findViewById<TextView>(R.id.tvJudulPengumuman)
        var _tanggal = itemView.findViewById<TextView>(R.id.tvDatePengumuman)
        var _text = itemView.findViewById<TextView>(R.id.tvTextPengumuman)
    }

    override fun onCreateViewHolder( parent: ViewGroup,  viewType: Int
    ): adapterpengumuman.ListViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.layoutpengumuman, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterpengumuman.ListViewHolder, position: Int) {
        var isi = listpengumuman[position]
        holder._judul.setText(isi.judul)
        holder._tanggal.setText(isi.tanggal)
        holder._text.setText(isi.text)
    }

    override fun getItemCount(): Int {
        return listpengumuman.size
    }

}
