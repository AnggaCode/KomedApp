package com.komed.komed.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.komed.komed.DataModel.ResponseCoba2Item
import com.komed.komed.R


class BangunanAdapter(private val item_bangunan: List<ResponseCoba2Item>) :
    RecyclerView.Adapter<BangunanAdapter.ViewHolder>() {
    private lateinit var mlistener: onItemClickedListener

    interface onItemClickedListener {
        fun onItemClickListener(position: Int)
    }

    fun setOnItemClicListener(listener: onItemClickedListener) {
        mlistener = listener
    }

    class ViewHolder(item_view: View, listener: onItemClickedListener) :
        RecyclerView.ViewHolder(item_view) {
        val image: ImageView = item_view.findViewById(R.id.image_list2)

        init {
            item_view.setOnClickListener {
                listener.onItemClickListener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_bangunan, parent, false)
        return ViewHolder(view, mlistener)
    }

    override fun getItemCount(): Int = item_bangunan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = item_bangunan[position]

        Glide.with(holder.image)
            .load(data.url)
            .into(holder.image)
    }
}