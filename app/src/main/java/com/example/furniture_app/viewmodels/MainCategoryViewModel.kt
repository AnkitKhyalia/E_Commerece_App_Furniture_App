package com.example.furniture_app.viewmodels

import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.furniture_app.data.CartProduct
import com.example.furniture_app.data.Product
import com.example.furniture_app.firebase.FirebaseCommon
import com.example.furniture_app.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    val db:FirebaseFirestore,
    val auth:FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
):ViewModel(){

    private val _allproducts = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val allproducts:StateFlow<Resource<List<Product>>> = _allproducts
   private val _productsByCategory = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    var currentdisplayproduct = mutableStateOf(Product())
    val productsByCategory:StateFlow<Resource<List<Product>>> = _productsByCategory
    val loading = mutableStateOf(false)
    val error = mutableStateOf<Exception?>(null)
//    val listState = rememberLazyListState(0,0)
    var lastVisible: DocumentSnapshot? = null

    private  val _addToCart = MutableStateFlow<Resource<CartProduct>>(Resource.Loading())
    val addToCart = _addToCart.asStateFlow()
    init {
        getallproducts()
    }
    private val query = db.collection("Products").limit(10)
    fun getallproducts(){
        db.collection("Products").limit(5).get().addOnSuccessListener {result->
//            Log.d("ProductData", "Result: $result")
            for (document in result.documents) {
                Log.d("ProductData", "Document: $document")
            }
            val allProductList = result.toObjects(Product::class.java)
            Log.d("products", allProductList[0].name)
            lastVisible= result.documents.lastOrNull()
            viewModelScope.launch {
                _allproducts.emit(Resource.Success(allProductList))
            }

        }.addOnFailureListener {
            viewModelScope.launch {
                _allproducts.emit(Resource.Error(it.message.toString()))
            }
        }
    }

    fun GetProductsByCategories(category:String){
        db.collection("Products")
            .whereEqualTo("category",category)
            .get()
            .addOnSuccessListener { result->
                val allProductList = result.toObjects(Product::class.java)
                Log.d("products", allProductList[0].name)

                viewModelScope.launch {
                    _productsByCategory.emit(Resource.Success(allProductList))
                }


            }.addOnFailureListener {
                viewModelScope.launch {
                    _productsByCategory.emit(Resource.Error(it.message.toString()))
                }
            }

    }
    fun loadMoreAllProducts(category: String){
    Log.d("paging","INSIDE LOADMOREALLPRODUCTS")

        if(category=="Main") {


            loading.value = true
            lastVisible?.let {
                db.collection("Products").startAfter(it).get().addOnSuccessListener { result ->
                    for (document in result.documents) {
                        Log.d("ProductData", "Document: $document")
                    }
                    val newProductList = result.toObjects(Product::class.java)
                    Log.d("products", newProductList[0].name)
                    lastVisible = result.documents.lastOrNull()

                    viewModelScope.launch {
                        val combinedList = _allproducts.value.data.orEmpty() + newProductList
                        _allproducts.emit(Resource.Success(combinedList))


                    }

                }.addOnFailureListener {
                    viewModelScope.launch {
                        _allproducts.emit(Resource.Error(it.message.toString()))
                    }
                }
            }
        }
        else{
            loading.value = true
            lastVisible?.let {
                db.collection("Products").whereEqualTo("category",category).startAfter(it).get().addOnSuccessListener { result ->
                    for (document in result.documents) {
                        Log.d("ProductData", "Document: $document")
                    }
                    val newProductList = result.toObjects(Product::class.java)
                    Log.d("products", newProductList[0].name)
                    lastVisible = result.documents.lastOrNull()

                    viewModelScope.launch {
                        val combinedList = _productsByCategory.value.data.orEmpty() + newProductList
                        _productsByCategory.emit(Resource.Success(combinedList))


                    }

                }.addOnFailureListener {
                    viewModelScope.launch {
                        _productsByCategory.emit(Resource.Error(it.message.toString()))
                    }
                }
            }

        }





    }
    fun addUpdateProductInCart(cartProduct: CartProduct){
        viewModelScope.launch { _addToCart.emit(Resource.Loading()) }
        db.collection("user").document(auth.uid!!)
            .collection("cart").whereEqualTo("product.id",cartProduct.product.id).get()
            .addOnSuccessListener {
                it.documents.let {
                    if(it.isEmpty()){ // add new product
                        addNewProdcut(cartProduct)
                    }
                    else{
                        val product = it.first().toObject(CartProduct::class.java)
                        if(product == cartProduct){// increase the quantity
                            val documentId = it.first().id
                            increaseQuantity(documentId,cartProduct)
                        }
                        else{//add new product
                            addNewProdcut(cartProduct)
                        }

                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch{
                    _addToCart.emit(Resource.Error(it.message.toString()))
                }

            }
    }
    private fun addNewProdcut(cartProduct: CartProduct){
        firebaseCommon.addProduct(cartProduct){addedProduct,e->
            viewModelScope.launch {
                if(e == null){
                    _addToCart.emit(Resource.Success(addedProduct!!))

                }
                else{
                    _addToCart.emit(Resource.Error(e.message.toString()))
                }
            }

        }
    }
    private fun increaseQuantity(documentId:String,cartProduct: CartProduct){
        firebaseCommon.increaseQuantity(documentId){_,e->
            viewModelScope.launch {
                if(e == null){
                    _addToCart.emit(Resource.Success(cartProduct!!))

                }
                else{
                    _addToCart.emit(Resource.Error(e.message.toString()))
                }
            }

        }
    }

}