package com.example.compras.controll

import android.util.Log
import com.example.compras.model.Item
import com.example.compras.model.ShopList

object ItemControll {

    private var currentItem: Item? = null
    private var itemsFiltered = ListControll.getCurrentList()?.items

    fun setCurrentItem(item: Item){
        currentItem = item
    }

    fun getItems() : MutableList<Item>? {
        return ListControll.getCurrentList()?.items
    }

    fun adicionarItem(nome: String, categoria: String, qnt: Int, un: String){

        val item = Item(nome, un, qnt, categoria)

        ListControll.addItemInList(item)

        updateItems()
    }

    fun deleteItems(shopList: ShopList){

        shopList.items = null
    }

    fun updateItems() {

        itemsFiltered = ListControll.getCurrentList()?.items
    }
}