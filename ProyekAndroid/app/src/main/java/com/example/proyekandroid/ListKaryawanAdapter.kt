package com.example.proyekandroid

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import java.io.File

class ListKaryawanAdapter(private val mItems: ArrayList<Karyawan>) : RecyclerView.Adapter<ListKaryawanAdapter.ViewHolder>() {
    private lateinit var mClickListener: OnItemClickListener
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_karyawan, parent, false)

        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun showDetail(position: Int, dataAdapter: ListKaryawanAdapter)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvNama.text = mItems[position].name
        holder.tvNoHp.text = mItems[position].telp
        Log.e("file", "path ${mItems[position].pic}")
        Glide.with(holder.llContainer)
            .load(Uri.fromFile(File(mItems[position].pic)))
            .circleCrop()
            .placeholder(R.drawable.ic_user)
            .into(holder.ivPhotoItem)
        holder.llContainer.setOnClickListener {
            mClickListener.showDetail(position, this)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView
        val tvNoHp:TextView
        val ivPhotoItem:ShapeableImageView
        val llContainer: LinearLayout
        init {
            tvNama = view.findViewById(R.id.tvNama)
            tvNoHp = view.findViewById(R.id.tvNoHp)
            ivPhotoItem = view.findViewById(R.id.ivPhotoItem)
            llContainer = view.findViewById(R.id.llContainer)
        }
    }
}