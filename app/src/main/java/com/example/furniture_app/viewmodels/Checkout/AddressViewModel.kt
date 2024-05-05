package com.example.furniture_app.viewmodels.Checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniture_app.data.Address
import com.example.furniture_app.util.AddressFieldsState
import com.example.furniture_app.util.AddressValidation
import com.example.furniture_app.util.Resource
import com.example.furniture_app.util.validateAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class AddressViewModel @Inject constructor(
    private val db :FirebaseFirestore,
    val auth: FirebaseAuth
):ViewModel() {
    private val _address = MutableStateFlow<Resource<Address>>(Resource.Unspecified())
    val address = _address.asStateFlow()
    private val _validation = Channel<AddressFieldsState> ()
    val validation = _validation.receiveAsFlow()
    init {
        viewModelScope.launch {
            _address.emit(Resource.Unspecified())
        }
    }
    fun addNewAddress(address: Address){
        viewModelScope.launch {
            _address.emit(Resource.Loading())
        }
        Log.d("Address View Model", "Inside addNewAddress function")

        if(shouldSave(address)) {

            db.collection("user").document(auth.uid!!).collection("address").document().set(address)
                .addOnSuccessListener {
                    Log.d("Address View Model", "Inside addNewAddress function success listner")
                    viewModelScope.launch {

                        _address.emit(Resource.Success(address))
                    }
                }.addOnFailureListener {
                    Log.d("Address View Model", "Inside addNewAddress function failure listner")
                    viewModelScope.launch {
                        _address.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
        else{
            val addressFieldsState = AddressFieldsState(
                AddressValidation.Success,
                validateAddress(address.name),
                validateAddress(address.street),
                validateAddress(address.city),
                validateAddress(address.state),
                validateAddress(address.pincode),
                validateAddress(address.phone),

            )
            runBlocking {
                _validation.send(addressFieldsState)
            }
        }
    }
    fun shouldSave(address: Address):Boolean{
        return validateAddress(address.name) is AddressValidation.Success &&
                validateAddress(address.street) is AddressValidation.Success &&
                validateAddress(address.city) is AddressValidation.Success &&
                validateAddress(address.state) is AddressValidation.Success &&
                validateAddress(address.pincode) is AddressValidation.Success &&
                validateAddress(address.phone) is AddressValidation.Success &&
                validateAddress(address.phone) is AddressValidation.Success
    }



}