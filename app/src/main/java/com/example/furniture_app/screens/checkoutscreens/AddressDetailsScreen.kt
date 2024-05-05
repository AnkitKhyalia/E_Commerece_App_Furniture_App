package com.example.furniture_app.screens.checkoutscreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.furniture_app.data.Address
import com.example.furniture_app.util.Header
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.Checkout.AddressDetailsViewModel
import java.util.UUID

@Composable
fun AddressDetailsScreen(navController: NavController,
                         addressDetailsViewModel: AddressDetailsViewModel = hiltViewModel()
){
    val addresslist = addressDetailsViewModel.addresslist.collectAsState()
    val noAddressAdded = addressDetailsViewModel.noAddressAdded.collectAsState()
    var selected = remember {
        mutableStateOf<Int>(-1)
    }
    val selectaddressindex = addressDetailsViewModel.selectedaddressindex.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(onClick = {navController.popBackStack()}, heading ="Address Details" )
        AddressHeader {

            navController.navigate("Add_New_Address_Screen")
        }
        if(selectaddressindex.value !=-1){
            Log.d("a","${addresslist.value.data?.get(selectaddressindex.value)}")
        }


        if (noAddressAdded.value) {
            NoAddress()
        } else {
            if (addresslist.value is Resource.Success) {
                AddressListComposable((addresslist.value as Resource.Success).data,selectaddressindex,addressDetailsViewModel) {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "address",
                        value = addresslist.value.data?.get(selectaddressindex.value)

                    )
                    navController.navigate(
                        "Billing_Screen"
                    )
                }
            } else if (addresslist.value is Resource.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(50.dp)
                )
            } else if (addresslist.value is Resource.Error) {
                ErrorComposable(addresslist.value.message.toString())
            }
        }

    }
}


@Composable
fun AddressHeader(
    OnClick:()->Unit
){
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Address", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        OutlinedButton(onClick = OnClick) {
            Icon(imageVector = Icons.Default.Add, contentDescription ="" )
            Text(text = "Add New Address")
        }
    }

}
@Composable
fun NoAddress(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Text(text = "No Address Added")
    }
}

@Composable
fun AddressListComposable(
    addresslist :List<Address>?,
    selected: State<Int>,
    addressDetailsViewModel: AddressDetailsViewModel,
    OnClick: () -> Unit

){

    val listState = rememberLazyListState()
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {

        Box(modifier = Modifier.weight(0.9f)) {


            LazyColumn(contentPadding = PaddingValues(20.dp), state = listState) {
                if (addresslist != null) {

                    items(count = addresslist.size,key = {UUID.randomUUID()}) { index ->
                        val address = addresslist[index]
                        EachAddress(
                            type = address.type,
                            name = address.name,
                            address.street,
                            address.city,
                            address.state,
                            address.pincode,
                            address.phone,
                            index, selected,
                        ){
                            addressDetailsViewModel.selectedaddress(index)

                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }


                }
            }
        }
//        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.weight(0.1f)) {
        Button(
            onClick = {
                if (selected.value != -1) {
                    OnClick.invoke()
                } else {
                    Toast.makeText(context, "Please Select a Address", Toast.LENGTH_SHORT).show()
                }

            },
            modifier = Modifier.fillMaxWidth()


        ) {

            Text(text = "Proceed")

        }
    }
    }

}

@Composable
fun ErrorComposable(error:String){

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Text(text = "Something Went Wrong ${error}")
    }
}

@Preview
@Composable
fun previewAddressHeader(){
    val  onClick : () -> Unit = {}
    AddressHeader(onClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachAddress(
     type:String,
     name:String,
     street:String,
     city:String,
     state:String,
     pincode:String,
     phone:String,
     index:Int,
     selected:State<Int>,
     OnClick: () -> Unit
){
    Card(onClick = OnClick,
        border = if(selected.value == index)
        { BorderStroke(2.dp, Color.Red)}
        else{
            BorderStroke(2.dp, Color.Black)
        },
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(text = "Type: $type", modifier = Modifier.padding(10.dp,4.dp))
        Text(text = "Name: $name", modifier = Modifier.padding(10.dp,4.dp))
        Text(text = "Street: $street", modifier = Modifier.padding(10.dp,4.dp))
        Text(text = "City: $city", modifier = Modifier.padding(10.dp,4.dp))
        Text(text = "State: $state", modifier = Modifier.padding(10.dp,4.dp))
        Text(text = "PinCode: $pincode", modifier = Modifier.padding(10.dp,4.dp))
        Text(text = "Phone: $phone", modifier = Modifier.padding(10.dp,4.dp))


    }
}


//@Preview
//@Composable
//fun previewEachAddress () {
//    val selected  = remember {
//        mutableStateOf<Int>(0)
//    }
//        EachAddress(
//            type = "a",
//            name = "b",
//            street = "c",
//            city = "d",
//            state = "e",
//            pincode = "f",
//            phone = "g",
//            index = 0,
//            selected = selected
//        )
//}

