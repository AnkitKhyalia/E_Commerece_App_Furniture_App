package com.example.furniture_app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address (
    var type:String,
    var name:String,
    var street:String,
    var city:String,
    var state:String,
    var pincode:String,
    var phone:String

):Parcelable{
    constructor():this("","","","","","","")
}