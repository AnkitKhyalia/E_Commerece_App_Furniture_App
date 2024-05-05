package com.example.furniture_app.screens.checkoutscreens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.furniture_app.data.Address
import com.example.furniture_app.util.AddressFieldsState
import com.example.furniture_app.util.AddressValidation
import com.example.furniture_app.util.Header
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.Checkout.AddressViewModel
val options = listOf("Home", "Work", "Other")
@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewAddressScreen(
    navController: NavController,
    addressViewModel: AddressViewModel = hiltViewModel()
) {
    var name by remember {
        mutableStateOf("")
    }
    var street by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var state by remember {
        mutableStateOf("")
    }
    var pincode by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }
    val addresssaved = addressViewModel.address.collectAsState().value
    val errorstates by addressViewModel.validation.collectAsState(initial = AddressFieldsState())
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Header(onClick = { navController.popBackStack() }, heading = "Add Address")

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
                            text = "Enter Address \n Details Below",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )


                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
//                            modifier = Modifier.fillMaxSize()
                        ) {
                            TextField(
                                value = selectedOption.value,
                                onValueChange = {
//                                selectedOption.value = it
                                },
                                readOnly = true,
                                label = { Text("Choose an option") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                options.forEach {
                                    Card(onClick = { selectedOption.value = it }) {
                                        Text(text = it)
                                    }
                                }
                            }
                        }


                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                                errorstates.name = AddressValidation.Success
                            },
                            label = { Text(text = "Name") },
                            isError = (errorstates.name != AddressValidation.Success),
                            supportingText = {
                                if ((errorstates.name != AddressValidation.Success)) {
                                    Text(text = "Name can not be empty")
                                }
                            }

                        )

                        OutlinedTextField(
                            value = street,
                            onValueChange = {
                                street = it
                                errorstates.street = AddressValidation.Success
                            },
                            label = { Text(text = "Street Name") },
                            isError = (errorstates.street != AddressValidation.Success),
                            supportingText = {
                                if ((errorstates.street != AddressValidation.Success)) {
                                    Text(text = "Street Name can not be empty")
                                }
                            }

                        )
                        OutlinedTextField(
                            value = city,
                            onValueChange = {
                                city = it
                                errorstates.city = AddressValidation.Success
                            },
                            label = { Text(text = "City") },
                            isError = (errorstates.city != AddressValidation.Success),
                            supportingText = {
                                if ((errorstates.city != AddressValidation.Success)) {
                                    Text(text = "City can not be empty")
                                }
                            }

                        )
                        OutlinedTextField(
                            value = state,
                            onValueChange = {
                                state = it
                                errorstates.state = AddressValidation.Success
                            },
                            label = { Text(text = "State") },
                            isError = (errorstates.state != AddressValidation.Success),
                            supportingText = {
                                if ((errorstates.state != AddressValidation.Success)) {
                                    Text(text = "State can not be empty")
                                }
                            }

                        )
                        OutlinedTextField(
                            value = pincode,
                            onValueChange = {
                                pincode = it
                                errorstates.pincode = AddressValidation.Success
                            },
                            label = { Text(text = "Pincode") },
                            isError = (errorstates.pincode != AddressValidation.Success),
                            supportingText = {
                                if ((errorstates.pincode != AddressValidation.Success)) {
                                    Text(text = "Pincode can not be empty")
                                }
                            }

                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = {
                                phone = it
                                errorstates.phone = AddressValidation.Success
                            },
                            label = { Text(text = "Phone") },
                            isError = (errorstates.phone != AddressValidation.Success),
                            supportingText = {
                                if ((errorstates.phone != AddressValidation.Success)) {
                                    Text(text = "Phone can not be empty")
                                }
                            }

                        )
                        Spacer(modifier = Modifier.height(40.dp))

                        Button(
                            onClick =
                            {
                                addressViewModel.addNewAddress(
                                    address = Address(
                                        selectedOption.value.toString(),
                                        name,
                                        street,
                                        city,
                                        state,
                                        pincode,
                                        phone
                                    )
                                )

                            },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth(0.73f)

                        ) {
                            Text(text = "Add Address", fontSize = 20.sp)

                        }

                    }


                }

            }
        }
        when (addresssaved) {
            is Resource.Success -> {
//                            Text("Address saved successfully!")
                Log.d("New Address Screen", "Address Saved Successfully")
                LaunchedEffect(key1 = Unit) {
                    Toast.makeText(
                        context,
                        "AddressSaved Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                navController.popBackStack("Address_Details_Screen",inclusive = false)

            }

            is Resource.Error -> {
                Text("Error: ${addressViewModel.address.value.message.toString()}")
                Log.d("AddNewAddressScreen", "Inside error block")

            }

            is Resource.Loading -> {
                Log.d("AddNewAddressScreen", "Inside loading block")
                CircularProgressIndicator()

            }

            else -> {
                Log.d("AddNewAddressScreen", "Inside else block")
            }
        }

    }




}