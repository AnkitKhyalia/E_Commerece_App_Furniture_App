package com.example.furniture_app.util

sealed class RegisterValidation(){
    object Success:RegisterValidation()
    data class Failed(val message:String):RegisterValidation()
}

data class RegisterFieldsState(
    var email:RegisterValidation =RegisterValidation.Success,
    var password:RegisterValidation = RegisterValidation.Success,
    var firstname:RegisterValidation = RegisterValidation.Success
)


