package com.example.furniture_app.viewmodels

import android.net.Uri
import android.util.Log
import android.widget.Toast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furniture_app.data.Product
import com.example.furniture_app.util.ProductListingValidation
import com.example.furniture_app.util.RegisterFieldsState
import com.example.furniture_app.util.SaveProductFieldsState
import com.example.furniture_app.util.SaveProductResult
import com.example.furniture_app.util.validateFirstName
import com.example.furniture_app.util.validateProduct
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SaveProductViewmodel @Inject constructor(
    private val db: FirebaseFirestore
):ViewModel(){
    private val _validation = Channel<SaveProductFieldsState> ()
    val validation = _validation.receiveAsFlow()

    private val _saveProductResult = MutableStateFlow<SaveProductResult?>(null)
    val saveProductResult: StateFlow<SaveProductResult?> = _saveProductResult

    fun saveProduct(product:Product,images: List<Uri>){
        if(shouldSave(product)){

            viewModelScope.launch {
                Log.d("inside save product" ,"${images.size}")
//                product.Id= randomUuID
                uploadProductWithImages(product, images,"Products")
            }

        }
        else{
            val saveProductFieldsState= SaveProductFieldsState(
                validateProduct(product.name),
                validateProduct(product.category),
                validateProduct(product.description),
                validateProduct(product.price)

            )
            runBlocking {
                _validation.send(saveProductFieldsState)
            }

        }

    }
    fun shouldSave(product: Product):Boolean{
        return validateProduct(product.name) is ProductListingValidation.Success &&
                validateProduct(product.category) is ProductListingValidation.Success &&
                validateProduct(product.description) is ProductListingValidation.Success &&
                validateProduct(product.price) is ProductListingValidation.Success


    }
    suspend fun uploadProductWithImages(product: Product, images: List<Uri>, collectionPath: String) {
        val storageRef = Firebase.storage.reference
        val uploadedImageUrls = mutableListOf<String>()
        product.id = "${product.name}_${System.currentTimeMillis()}"
        for (imageUri in images) {
            val fileName = "${product.name}_${System.currentTimeMillis()}"
            val imageRef = storageRef.child("products/images/$fileName")

            try {
                val uploadTask = imageRef.putFile(imageUri)
                uploadTask.await()
                uploadTask.addOnSuccessListener {
                    // Image upload successful
                    Log.d("application", "Image uploaded successfully!")
                }.addOnFailureListener { e ->
                    // Image upload failed
                    Log.d("application", "Image upload failed: $e")
                    _saveProductResult.value = SaveProductResult.Error("Failed to upload images.")
                }

                val downloadUrl = imageRef.downloadUrl.await()
                uploadedImageUrls.add(downloadUrl.toString())
            } catch (e: Exception) {
                // Handle exceptions related to image upload
                _saveProductResult.value = SaveProductResult.Error("Failed to upload images: $e")
            }
        }

            val productWithImages = product.copy(images = uploadedImageUrls)

            try {
                db.collection(collectionPath)
                    .add(productWithImages)
                    .await()

                _saveProductResult.value = SaveProductResult.Success
            } catch (e: Exception) {
                // Handle exceptions related to Firestore database
                _saveProductResult.value = SaveProductResult.Error("Failed to save product: $e")
            }


    }



}