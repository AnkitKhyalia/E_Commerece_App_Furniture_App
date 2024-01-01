package com.example.furniture_app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniture_app.R
import com.example.furniture_app.data.CartProduct
import com.example.furniture_app.firebase.FirebaseCommon
import com.example.furniture_app.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
   private val db:FirebaseFirestore,
    private val auth:FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
):ViewModel() {

    val _totalprice = MutableLiveData<Double>(0.0)
    val totalprice = _totalprice



    private val _allCartProducts = MutableStateFlow<Resource<List<CartProduct>>>(Resource.Loading())
    val allCartProducts = _allCartProducts.asStateFlow()

    private var cartProductDocument = emptyList<DocumentSnapshot>()
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
                    cartProductDocument = result.documents
                }

                }
            }
//            .addOnSuccessListener {result->
//                val allcartproductslist =   result.toObjects(CartProduct::class.java)
//                viewModelScope.launch{
//                    _allCartProducts.emit(Resource.Success(allcartproductslist))
//                    gettoalprice(allcartproductslist)
//                    cartProductDocument = result.documents
//                }
//            }.addOnFailureListener {
//                viewModelScope.launch {
//                    _allCartProducts.emit(Resource.Error(it.message.toString()))
//                }
//            }

    }
    fun gettoalprice(allcartprodcuts:List<CartProduct>?){
        if (allcartprodcuts != null) {
            _totalprice.value=0.0
            allcartprodcuts.forEach { cartProduct ->
                _totalprice.value = _totalprice.value?.plus(cartProduct.product.price.toInt() * cartProduct.quantity)
            }
        }

    }
    fun changeQuantity(
        cartProduct: CartProduct,
        quantityChanging:FirebaseCommon.QuantityChanging
    ){
        val index = allCartProducts.value.data?.indexOf(cartProduct)
        if(index!=null && index >=0 ) {


            val documedId = cartProductDocument[index].id
            when(quantityChanging){
                FirebaseCommon.QuantityChanging.INCREASE ->{
                    viewModelScope.launch {
                        _allCartProducts.emit(Resource.Loading())
                    }
                            increaseQuantity(documedId)
                }
                FirebaseCommon.QuantityChanging.DECREASE ->{
                    viewModelScope.launch {
                        _allCartProducts.emit(Resource.Loading())
                    }
                            decreaseQuantity(documedId)
                }
            }
        }


    }
    fun deleteitem(cartProduct: CartProduct){
        val index = allCartProducts.value.data?.indexOf(cartProduct)
        if(index!=null && index >=0 ) {


            val documentId = cartProductDocument[index].id
            db.collection("user").document(auth.uid!!).collection("cart").document(documentId).delete()


        }

    }

    private fun decreaseQuantity(documentId: String) {
            firebaseCommon.decreaseQuantity(documentId){result,e ->
                if(e !=null){
                    viewModelScope.launch {
                        _allCartProducts.emit(Resource.Error(e.message.toString()))
                    }
                }

            }
    }

    private fun increaseQuantity(documentId: String) {
        firebaseCommon.increaseQuantity(documentId){result,e ->
            if(e !=null){
                viewModelScope.launch {
                    _allCartProducts.emit(Resource.Error(e.message.toString()))
                }
            }

        }
    }


}