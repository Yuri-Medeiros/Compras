package com.example.compras.model

import android.net.Uri

data class ShopList(

    var title: String,
    var img: Uri? = null,
    var items: MutableList<Item>? = null
)
