package com.example.furniture_app.viewmodels.mainapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniture_app.data.Order
import com.example.furniture_app.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val db:FirebaseFirestore,
    private val auth: FirebaseAuth
):ViewModel() {
    private val _orders = MutableStateFlow<Resource<List<Order>>>(Resource.Unspecified())
    val order = _orders.asStateFlow()

    init {
        getAllOrders()
    }
    fun getAllOrders(){
        viewModelScope.launch {
            _orders.emit(Resource.Loading())
        }
        db.collection("user").document(auth.uid!!).collection("orders").get()
            .addOnSuccessListener { result->
                for (document in result.documents) {
                    Log.d("Order Data", "Document: $document")
                }
                val allorderlist = result.toObjects(Order::class.java)
                Log.d("oderes", allorderlist[0].totalPrice.toString())
                viewModelScope.launch {
                    _orders.emit(Resource.Success(allorderlist))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _orders.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}