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

    fun getShopLists(): MutableList<ShopList> {
        return shopLists
    }

    fun getShopList(title: String, img: Uri?): ShopList? {

        var shopList: ShopList?

        if (img == null) {
            shopList = shopLists.find { it.title == title }
        } else {
            shopList = shopLists.find { it.title == title && it.img == img }
        }

        return shopList
    }

    fun adicionarList(title: String, img: Uri?) {

        if (getShopList(title, img) == null){

            shopLists.add(ShopList(title, img))
            updateList()

        } else {

            throw IllegalArgumentException("DUPLICADO")
        }
    }

    fun editList(oldList: ShopList, newTitle: String, img: Uri?) {

        if (oldList.title != newTitle && shopLists.find { it.title == newTitle } != null) {
            throw IllegalArgumentException("DUPLICADO")
        }

        val sl = shopLists.find { it == oldList }
        sl?.title = newTitle
        sl?.img = img
        updateList()
    }

    fun deleteList(shopList: ShopList) {

        ItemControll.deleteItems(shopList)
        shopLists.remove(shopList)
        updateList()
    }

    fun updateList() {
        listFiltered = shopLists

        if (currentList != null){
            val shopList = shopLists.find { it.title == currentList?.title }
            currentList?.items = shopList?.items
        }
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

    fun setCurrentList(list: ShopList?) {
        currentList = list
    }
}