package com.example.proyekandroid

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class adapterpengajuan(
    private val listpengajuan : ArrayList<ClassCuti>
) : RecyclerView.Adapter<adapterpengajuan.ListViewHolder>(){

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClickedAccept (datax : ClassCuti)
        fun onItemClickedDenie (datax : ClassCuti)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var _date : TextView = itemView.findViewById<TextView>(R.id.tvAcceptDate)
        var _duration : TextView = itemView.findViewById<TextView>(R.id.tvAcceptDur)
        var _user : TextView = itemView.findViewById<TextView>(R.id.tvAcceptUser)
        var _description : TextView = itemView.findViewById<TextView>(R.id.tvAcceptDesc)
        var _btnAccept : ImageView = itemView.findViewById(R.id.btnAcceptAcc)
        var _btnDenied : ImageView = itemView.findViewById(R.id.btnAcceptDenied)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterpengajuan.ListViewHolder {
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.layoutacceptizincuti,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterpengajuan.ListViewHolder, position: Int) {
        var isi = listpengajuan[position]
        holder._date.setText(isi.start + " - " + isi.end)
        holder._duration.setText(isi.durasi)
        holder._user.setText(isi.username)
        holder._description.setText(isi.keterangan)

        holder._btnAccept.setOnClickListener {
            onItemClickCallback.onItemClickedAccept(listpengajuan[position])
        }
        holder._btnDenied.setOnClickListener {
            onItemClickCallback.onItemClickedDenie(listpengajuan[position])
        }
    }

    override fun getItemCount(): Int {
        return listpengajuan.size
    }

}
