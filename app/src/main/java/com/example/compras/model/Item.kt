package com.example.compras.model

data class Item(

    var name: String,
    var unidade: String,
    var quantidade: Int,
    var categoria: String,
    var comprado: Boolean = false
)
