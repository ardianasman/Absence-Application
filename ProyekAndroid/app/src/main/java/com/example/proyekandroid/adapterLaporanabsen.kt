package com.example.proyekandroid

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class adapterLaporanabsen(
    private val listlaporan : ArrayList<Absensi>
) : RecyclerView.Adapter<adapterLaporanabsen.ListViewHolder>(){

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _date = itemView.findViewById<TextView>(R.id.tvDateLaporanAbsenKaryawan)
        var _status = itemView.findViewById<TextView>(R.id.tvStatusLaporanAbsenKaryawan)
    }

    override fun onCreateViewHolder( parent: ViewGroup,  viewType: Int ): adapterLaporanabsen.ListViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.layoutlaporanabsen,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterLaporanabsen.ListViewHolder, position: Int) {
        var isi = listlaporan[position]
        holder._date.setText(isi.tanggal)

        if(isi.check_in == false){
            holder._status.setBackgroundColor(Color.parseColor("#c20000"))
        }
        else if(isi.check_in == true){
            holder._status.setBackgroundColor(Color.parseColor("#24c200"))
        }
    }

    override fun getItemCount(): Int {
        return listlaporan.size
    }

}
