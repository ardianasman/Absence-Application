package com.example.proyekandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class ListKaryawanAdapter(private val mItems: ArrayList<Karyawan>) : RecyclerView.Adapter<ListKaryawanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListKaryawanAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_karyawan, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListKaryawanAdapter.ViewHolder, position: Int) {
        holder.tvNama.text = mItems[position].name
        holder.tvNoHp.text = mItems[position].phone
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView
        val tvNoHp:TextView
        val ivPhotoItem:ShapeableImageView
        init {
            tvNama = view.findViewById(R.id.tvNama)
            tvNoHp = view.findViewById(R.id.tvNoHp)
            ivPhotoItem = view.findViewById(R.id.ivPhotoItem)
        }
    }
}