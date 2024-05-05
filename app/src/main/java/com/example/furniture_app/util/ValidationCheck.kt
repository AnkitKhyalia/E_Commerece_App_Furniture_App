package com.example.furniture_app.util

import android.util.Patterns

fun validateEmail(email:String):RegisterValidation{
    if(email.isEmpty()){
        return RegisterValidation.Failed("Email cannot be empty")
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return RegisterValidation.Failed("Wrong email format")

    }
    return RegisterValidation.Success
}

fun validatePassword(password:String):RegisterValidation{
    if(password.isEmpty()){
        return RegisterValidation.Failed("Password Can not be empty")

    }
    if(password.length<6){
        return RegisterValidation.Failed("Password length should be more than 5")
    }

    return RegisterValidation.Success
}
fun validateFirstName(firstname:String):RegisterValidation{
    if(firstname.isEmpty()){
        return RegisterValidation.Failed("First Name can not be empty")
    }
    return RegisterValidation.Success
}
fun validateProduct(input:String):ProductListingValidation{
    if(input.isEmpty()){
        return ProductListingValidation.Failed("First Name can not be empty")
    }
    return ProductListingValidation.Success
}
fun validateAddress(input:String):AddressValidation{
    if(input.isEmpty()){
        return AddressValidation.Failed("First Name can not be empty")
    }
    return AddressValidation.Success
}