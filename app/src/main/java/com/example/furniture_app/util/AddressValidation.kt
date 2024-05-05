package com.example.furniture_app.util

sealed class AddressValidation{
    object Success : AddressValidation()
    data class Failed(val message:String):AddressValidation()
}

data class AddressFieldsState(
    var type:AddressValidation = AddressValidation.Success,
    var name:AddressValidation = AddressValidation.Success,
    var street:AddressValidation = AddressValidation.Success,
    var city:AddressValidation = AddressValidation.Success,
    var state:AddressValidation = AddressValidation.Success,
    var pincode:AddressValidation = AddressValidation.Success,
    var phone:AddressValidation = AddressValidation.Success
)