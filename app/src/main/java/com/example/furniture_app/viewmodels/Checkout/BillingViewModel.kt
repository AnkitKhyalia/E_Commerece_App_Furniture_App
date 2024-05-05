package com.example.furniture_app.viewmodels.Checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniture_app.data.CartProduct
import com.example.furniture_app.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillingViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
):ViewModel() {
    private val _allCartProducts = MutableStateFlow<Resource<List<CartProduct>>>(Resource.Loading())
    val allCartProducts = _allCartProducts.asStateFlow()
    val _totalprice = MutableLiveData<Double>(0.0)
    val totalprice = _totalprice

    init {
        getAllCartProducts()

    }
    fun getAllCartProducts(){
        viewModelScope.launch {
            _allCartProducts.emit(Resource.Loading())
        }
        db.collection("user").document(auth.uid!!).collection("cart")
            .addSnapshotListener{result,error->
                if(error !=null || result ==null){
                    viewModelScope.launch {
                        _allCartProducts.emit(Resource.Error(error?.message.toString()))
                    }
                }
                else{
                    val allcartproductslist =   result.toObjects(CartProduct::class.java)
                    viewModelScope.launch{
                        _allCartProducts.emit(Resource.Success(allcartproductslist))
                        gettoalprice(allcartproductslist)
//                        cartProductDocument = result.documents
                    }

                }
            }
    }
    fun gettoalprice(allcartprodcuts:List<CartProduct>?){
        if (allcartprodcuts != null) {
            _totalprice.value=0.0
            allcartprodcuts.forEach { cartProduct ->
                _totalprice.value = _totalprice.value?.plus(cartProduct.product.price.toInt() * cartProduct.quantity)
            }
        }

    }

}