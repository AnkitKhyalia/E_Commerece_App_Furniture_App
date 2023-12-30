package com.example.furniture_app.data

import android.net.Uri

data class Product(
    var id:String="",
    val name: String,
    val category: String,
    val description:String,
    val price:String,
    val offer:String?,
    val images:List<String> = emptyList<String>()
){
    constructor():this("","","","","","")
}
