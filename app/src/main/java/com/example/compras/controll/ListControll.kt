package com.example.compras.controll

import android.net.Uri
import com.example.compras.model.ShopList
import kotlin.collections.mutableListOf

object ListControll {

    private val shopLists = mutableListOf<ShopList>()
    private var listFiltered = shopLists

    fun getListFiltered(): MutableList<ShopList> {
        return listFiltered
    }

    fun adicionarList(title: String, img: Uri?) {

        val shopList = ShopList(title, img)

        shopLists.add(shopList)
    }

    fun removerList(shopList: ShopList) {
        shopLists.remove(shopList)
    }

    fun updateList() {
        listFiltered = shopLists
    }

    fun filter(search: String) {

        listFiltered = shopLists.filter { list ->

            val title = list.title.contains(search, ignoreCase = true)
            val items = list.items?.any {item ->
                item.name.contains(search, ignoreCase = true) ||
                item.categoria.contains(search, ignoreCase = true)
            }

            title || items != null

        }.toMutableList()
    }
}