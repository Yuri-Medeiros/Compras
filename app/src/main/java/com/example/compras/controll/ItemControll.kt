package com.example.compras.controll

import com.example.compras.controll.ListControll.updateList
import com.example.compras.model.Item
import com.example.compras.model.ShopList

object ItemControll {

    private var currentItem: Item? = null

    fun setCurrentItem(item: Item?){
        currentItem = item
    }

    fun getCurrentItem(): Item? {
        return currentItem
    }

    fun getItems() : MutableList<Item>? {
        return ListControll.getCurrentList()?.items
    }

    fun adicionarItem(nome: String, categoria: String, qnt: Int, un: String) {

        val item = Item(nome, un, qnt, categoria)

        val items = ListControll.getShopLists().find {it.title == ListControll.getCurrentList()?.title }

        if (items?.items == null) {

            items?.items = mutableListOf<Item>()
        } else {
            // Corrigido: verifica se já existe pelo nome
            if (getItems()?.any { it.name == nome } != null) {
                throw IllegalArgumentException("DUPLICADO")
            }
        }
        items?.items?.add(item)

        updateList()
    }

    fun editItem(nome: String, categoria: String, qnt: Int, un: String){

        if (currentItem?.name != nome && getItems()?.find { it.name == nome } != null) {
            throw IllegalArgumentException("DUPLICADO")
        }

        val sl = ListControll.getShopLists()
            .find { it.title == ListControll.getCurrentList()?.title }
            ?.items
            ?.find { it.name == getCurrentItem()?.name }
        sl?.name = nome
        sl?.categoria = categoria
        sl?.quantidade = qnt
        sl?.unidade = un
        updateList()
    }

    fun deleteItems(shopList: ShopList){

        shopList.items = null
    }

    fun deleteItem(){

        val item = ListControll.getShopLists()
            .find { it.title == ListControll.getCurrentList()?.title }
            ?.items
            ?.find { it.name == getCurrentItem()?.name }

        ListControll.getShopLists()
            .find { it.title == ListControll.getCurrentList()?.title }
            ?.items
            ?.remove(item)

        updateList()
    }
}