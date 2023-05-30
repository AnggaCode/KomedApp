package com.komed.komed.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komed.komed.DataModel.ResponseCoba3Item
import com.komed.komed.R

class TamanAdapter(private val item_taman: List<ResponseCoba3Item>) :
    RecyclerView.Adapter<TamanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_taman_hiburan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = item_taman.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = item_taman[position]
        Glide.with(holder.image_taman)
            .load(data.url)
            .into(holder.image_taman)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_taman: ImageView = itemView.findViewById(R.id.image_list3)
    }

}