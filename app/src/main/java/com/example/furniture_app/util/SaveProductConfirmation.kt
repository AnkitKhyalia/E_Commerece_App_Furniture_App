package com.example.furniture_app.util

sealed class SaveProductResult {
    object Success : SaveProductResult()
    data class Error(val message: String) : SaveProductResult()
}