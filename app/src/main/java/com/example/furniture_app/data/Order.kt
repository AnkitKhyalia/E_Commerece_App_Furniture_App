package com.example.furniture_app.data

data class Order(
    val orderstatus:String,
    val totalPrice:Int,
    val products:List<CartProduct>,
    val address: Address
){
    constructor():this("Pending",0, emptyList(),Address())
}

sealed class OrderStatus(val status:String){
    object Ordered:OrderStatus("Ordered")
    object Cancelled:OrderStatus("Cancelled")
    object Confirmed:OrderStatus("Confirmed")
    object Shipped:OrderStatus("Shipped")
    object Delivered:OrderStatus("Delivered")
    object Returned:OrderStatus("Returned")
}