package com.example.furniture_app.viewmodels.Checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniture_app.data.Address
import com.example.furniture_app.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressDetailsViewModel @Inject constructor(
    private val db:FirebaseFirestore,
    val auth:FirebaseAuth
):ViewModel() {

    private val _addresslist =MutableStateFlow<Resource<List<Address>>>(Resource.Unspecified())
     val addresslist = _addresslist.asStateFlow()
    private val _noAddressAdded = MutableStateFlow(false)
    val noAddressAdded = _noAddressAdded.asStateFlow()
    private val _selectedaddressindex = MutableStateFlow<Int>(-1)
    val selectedaddressindex = _selectedaddressindex.asStateFlow()

    init {
        getAddressDetails()
    }
    fun getAddressDetails(){
        db.collection("user").document(auth.uid!!).collection("address")
            .addSnapshotListener{snapshot,error->
                if(error != null){
                    if (error.code == FirebaseFirestoreException.Code.NOT_FOUND){

                        viewModelScope.launch {
                            _noAddressAdded.emit(true)
                        }

                    }
                    else{
                        viewModelScope.launch {
//                            _noAddressAdded.emit(false)
                            _addresslist.emit(Resource.Error(error.message.toString()))
                        }
                    }
                }
                else{

                    if (snapshot!=null && !snapshot.isEmpty){
                        val allAddressList = snapshot.toObjects(Address::class.java)
                        viewModelScope.launch {
                            _noAddressAdded.emit(false)
                            _addresslist.emit(Resource.Success(allAddressList))
                        }
                    }
                    else{
                        viewModelScope.launch {
                            _noAddressAdded.emit(true)
                        }

                    }
                }
            }
    }
    fun selectedaddress(index:Int){
        viewModelScope.launch {
            _selectedaddressindex.emit(index)
            Log.d("inside seleced address" ,"current ${selectedaddressindex.value},${_selectedaddressindex.value},$index")
        }
    }


}