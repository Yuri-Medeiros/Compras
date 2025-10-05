package com.example.compras.model

import android.net.Uri

data class ShopList(

    val title: String,
    val img: Uri? = null,
    var items: MutableList<Item>? = null
)
