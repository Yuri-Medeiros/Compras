package com.example.compras.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.compras.databinding.ActivityListBinding
import com.example.compras.model.ShopList

class ListAdapter(
    private var shopLists: MutableList<ShopList>,
    private val onItemClick: (ShopList) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {

        val binding = ActivityListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {

        val item = shopLists[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.ivImg.setImageURI(item.img)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = shopLists.size

    inner class ListViewHolder(val binding: ActivityListBinding) :
        RecyclerView.ViewHolder(binding.root)
}