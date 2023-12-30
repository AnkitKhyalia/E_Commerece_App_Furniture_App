package com.example.furniture_app.screens.appscreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.furniture_app.data.Product
import com.example.furniture_app.data.User
import com.example.furniture_app.util.ProductListingValidation
import com.example.furniture_app.util.RegisterFieldsState
import com.example.furniture_app.util.RegisterValidation
import com.example.furniture_app.util.SaveProductFieldsState
import com.example.furniture_app.util.SaveProductResult
import com.example.furniture_app.viewmodels.SaveProductViewmodel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    saveProductViewmodel: SaveProductViewmodel= hiltViewModel()
){
    var name by remember {
        mutableStateOf("")
    }
    var category by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var offer by remember {
        mutableStateOf("")
    }
    val errorstates by saveProductViewmodel.validation.collectAsState(initial = SaveProductFieldsState())
    val saveProductResult by saveProductViewmodel.saveProductResult.collectAsState()
    val context =LocalContext.current

    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }


    val imageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
        // Check if the user selected any images
        if (!uris.isNullOrEmpty()) {
            selectedImages = uris
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            // Permission is granted, launch the image picker
            imageLauncher.launch(arrayOf("image/*").joinToString())
        } else {
            // Permission is denied, show a message to the user
            Toast.makeText(context, "Please grant the permission to access images", Toast.LENGTH_SHORT).show()
        }
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {





        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Enter Product \n Details Below",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                errorstates.Name= ProductListingValidation.Success
                    },
                    label = { Text(text = "Name") },
                isError = (errorstates.Name != ProductListingValidation.Success),
                supportingText = {
                    if((errorstates.Name != ProductListingValidation.Success)){
                        Text(text = "Name can not be empty")
                    }
                }

                )

                OutlinedTextField(
                    value = category,
                    onValueChange = {
                        category = it
                errorstates.Category= ProductListingValidation.Success
                    },
                    label = { Text(text = "Category") },
                isError = (errorstates.Category != ProductListingValidation.Success),
                supportingText = {
                    if((errorstates.Category != ProductListingValidation.Success)){
                        Text(text = "Category can not be empty")
                    }
                }

                )
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                errorstates.Description= ProductListingValidation.Success
                    },
                    label = { Text(text = "Description") },
                isError = (errorstates.Description != ProductListingValidation.Success),
                supportingText = {
                    if((errorstates.Description != ProductListingValidation.Success)){
                        Text(text = "Description can not be empty")
                    }
                }

                )
                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        price = it
                errorstates.Price= ProductListingValidation.Success
                    },
                    label = { Text(text = "Price") },
                isError = (errorstates.Price != ProductListingValidation.Success),
                supportingText = {
                    if((errorstates.Price != ProductListingValidation.Success)){
                        Text(text = "Price can not be empty")
                    }
                }

                )
                OutlinedTextField(
                    value = offer,
                    onValueChange = {
                        offer = it

                    },
                    label = { Text(text = "Offer") },

                )
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = { imageLauncher.launch(arrayOf("image/*").joinToString()) },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(0.73f)

                ) {
                    Text(text = "Add Images", fontSize = 20.sp)

                }
                Button(
                    onClick = {
                        saveProductViewmodel.saveProduct(
                            product = Product(
                                "",
                                name,
                                category,
                                description,
                                price,
                                offer
                            ), selectedImages
                        )
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(0.73f)

                ) {
                    Text(text = "Submit", fontSize = 20.sp)

                }
                saveProductResult?.let { result ->
                    when (result) {
                        is SaveProductResult.Success -> {
                            Text("Product saved successfully!")
                        }
                        is SaveProductResult.Error -> {
                            Text("Error: ${result.message}")
                        }
                    }
                }

            }


    }

        }
    }

}

@Preview
@Composable
fun PreviewAddProductScreen(){
    AddProductScreen()
}