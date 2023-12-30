package com.example.furniture_app.util

sealed class ProductListingValidation{
    object Success : ProductListingValidation()
    data class Failed(val message:String):ProductListingValidation()
}
data class SaveProductFieldsState(
    var Name: ProductListingValidation=ProductListingValidation.Success,
    var Category: ProductListingValidation=ProductListingValidation.Success,
    var Description:ProductListingValidation=ProductListingValidation.Success,
    var Price:ProductListingValidation=ProductListingValidation.Success,
    var Offer:ProductListingValidation=ProductListingValidation.Success,
)


