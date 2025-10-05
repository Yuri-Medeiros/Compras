package com.example.compras.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.compras.databinding.ActivityItemBinding
import com.example.compras.model.Item
import kotlin.or

class ItemAdapter(
    private var items: MutableList<Item>?,
    private val onItemClick: (Item?) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {

        val binding = ActivityItemBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        val item: Item? = items?.get(position)

        val nome = holder.binding.tvNome
        val categoria = holder.binding.tvCat
        val qnt = holder.binding.tvQnt
        val unidade = holder.binding.tvUn

        nome.text = item?.name
        categoria.text = item?.categoria
        qnt.text = "(" + item?.quantidade.toString() + "x)"
        unidade.text = item?.unidade
        holder.binding.cbComprado.isChecked = item?.comprado == true

        fun atualizarVisual(){

            if (item?.comprado == true) {
                nome.setTextColor(Color.GRAY)
                nome.paintFlags = nome.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                categoria.setTextColor(Color.GRAY)
                categoria.paintFlags = categoria.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                qnt.setTextColor(Color.GRAY)
                qnt.paintFlags = qnt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                unidade.setTextColor(Color.GRAY)
                unidade.paintFlags = unidade.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            } else {

                nome.setTextColor(Color.BLACK)
                nome.paintFlags = nome.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                categoria.setTextColor(Color.BLACK)
                categoria.paintFlags = categoria.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                qnt.setTextColor(Color.BLACK)
                qnt.paintFlags = qnt.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                unidade.setTextColor(Color.BLACK)
                unidade.paintFlags = unidade.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        atualizarVisual()

        holder.binding.cbComprado.setOnClickListener {

            item?.comprado = holder.binding.cbComprado.isChecked
            atualizarVisual()
        }

        holder.itemView.setOnClickListener {

            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ItemViewHolder(val binding: ActivityItemBinding) :
         RecyclerView.ViewHolder(binding.root)
}