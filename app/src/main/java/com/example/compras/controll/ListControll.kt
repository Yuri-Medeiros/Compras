package com.example.compras.controll

import android.net.Uri
import com.example.compras.model.Item
import com.example.compras.model.ShopList
import kotlin.collections.mutableListOf

object ListControll {

    private val shopLists = mutableListOf<ShopList>()
    private var listFiltered = shopLists
    private var currentList: ShopList? = null

    fun getListFiltered(): MutableList<ShopList> {
        return listFiltered
    }

    fun getCurrentList(): ShopList? {
        return currentList
    }

    fun getShopList(shopList: ShopList): ShopList? {

        return shopLists.find { it == shopList }
    }

    fun adicionarList(title: String, img: Uri?) {

        val shopList = ShopList(title, img)

        if (getShopList(shopList) == null){

            shopLists.add(shopList)
            updateList()
        }
    }

    fun editList(oldList: ShopList, newTitle: String, img: Uri?) {

        val sl = shopLists.find { it == oldList }
        sl?.title = newTitle
        sl?.img = img
    }

    fun deleteList(shopList: ShopList) {

        ItemControll.deleteItems(shopList)
        shopLists.remove(shopList)
        updateList()
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

    fun addItemInList(item: Item){

        val list = shopLists.find { currentList?.title == it.title }

        if (list == null) return

        if (list.items != null) {
            list.items?.add(item)
        } else {
            list.items = mutableListOf<Item>(item)
        }

        updateList()
    }

    fun setCurrentList(list: ShopList?) {
        currentList = list
    }
}