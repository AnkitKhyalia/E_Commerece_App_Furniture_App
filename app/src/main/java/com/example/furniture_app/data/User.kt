package com.example.furniture_app.data

data class User(
    var firstName:String,
    var lastName:String,
    var email:String,
    var imagePath:String,
){
    constructor():this("","","","")
}
