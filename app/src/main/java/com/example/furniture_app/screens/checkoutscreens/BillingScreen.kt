package com.example.furniture_app.screens.checkoutscreens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.furniture_app.R
import com.example.furniture_app.data.Address
import com.example.furniture_app.data.CartProduct
import com.example.furniture_app.data.Order
import com.example.furniture_app.data.OrderStatus
import com.example.furniture_app.util.Header
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.Checkout.BillingViewModel
import com.example.furniture_app.viewmodels.Checkout.OrderViewModel
import java.util.UUID

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BillingScreen(
    navController: NavController,
    address: Address?,
    billingViewModel: BillingViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    Log.d("NavigationDebug", "Address received: $address")
    val allcartproductslist = billingViewModel.allCartProducts.collectAsState()
    val order = orderViewModel.order.collectAsState()
    val context = LocalContext.current
//    PrintBackStack(navController = navController)
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Header(onClick = {navController.popBackStack()}, heading ="Billing" )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart){
                Column (Modifier.fillMaxWidth()) {


                    Text(text = "Payment Method: ")
                    Text(
                        text = "Currently We do not accept any Virtual Payment,Please Pay when you Receive the order",
                        modifier = Modifier
                            .basicMarquee()
                            .padding(20.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(text = "Selected Address: ")
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.2f)) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                    if (address != null) {
                        ShowAddress(
                            type = address.type,
                            name = address.name,
                            street = address.street,
                            city = address.city,
                            state = address.state,
                            pincode = address.pincode,
                            phone = address.phone,

                            )
                    }

                }
            }
            Text(text = "List of Products:")
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.8f)) {
                Column(modifier = Modifier.fillMaxWidth()) {

                    if (allcartproductslist.value is Resource.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(40.dp)
                        )
                    } else if (allcartproductslist.value is Resource.Success) {
                        val productlist = (allcartproductslist.value as Resource.Success).data
                        BillingProductList(products = productlist)
                    } else if (allcartproductslist.value is Resource.Error) {
                        (allcartproductslist.value as Resource.Error).message?.let { Text(it) }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.5f)
                .padding(20.dp, 5.dp)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Total Price : ${billingViewModel.totalprice.value}",
                        fontWeight = FontWeight.Bold,

                    )
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {

                    billingViewModel.totalprice.value?.let {
                        allcartproductslist.value.data?.let { it1 ->
                            if (address != null) {
                                orderViewModel.placeOrder(
                                order = Order(
                                    orderstatus =OrderStatus.Confirmed.status ,
                                    totalPrice = it.toInt() ,
                                    products = it1,
                                    address=address

                                )
                                )
                            }
                        }
                    }

                  },Modifier.fillMaxWidth()) {
                    Text(text = "Place Order")
                }
            }
            when(order.value){
                is Resource.Loading ->{
                    CircularProgressIndicator()
                }
                is Resource.Error ->{
                    Text(text = order.value.message.toString())
                }
                is Resource.Success -> {
                    LaunchedEffect(key1 = Unit) {
                        Toast.makeText(context,"Order Successfully Placed",Toast.LENGTH_SHORT).show()
                    }
//                    navController.popBackStack(navController.graph.id,inclusive = true)
//                    navController.popBackStack()


                    navController.navigate("Shopping_Cart_Screen") {
                        popUpTo("Checkout_Navigation_Graph") {
                            inclusive = true // this will pop the start destination of the graph as well
                        }
                    }
//                    val parentNavController = navController.getBackStackEntry("Checkout_Navigation_Graph")
//                        ?.destination?.parent?.findNavController()
//
//                    if (parentNavController != null) {
//                        parentNavController.popBackStack("Checkout_Navigation_Graph", inclusive = false)
//                        parentNavController.navigate("Shopping_Cart_Screen")
//                    }
//                    SideEffect {
//                        Toast.makeText(context,"Order Successfully Placed",Toast.LENGTH_SHORT).show()
//                    }
//                    Toast.makeText(context,"Order Successfully Placed",Toast.LENGTH_SHORT).show()
//                    printNavBackStack(navController)
//                    navController.popBackStack(
//                        "Shopping_Cart_Screen",inclusive = false
//                    )
//                    navController.navigate("Shopping_Cart_Screen"){
//                        popUpTo(0){
//
//                        }
//                    }
                }
                else -> {

                }
            }
        }



    }

}
@Composable
fun ShowAddress(
    type:String,
    name:String,
    street:String,
    city:String,
    state:String,
    pincode:String,
    phone:String
){
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(Modifier.fillMaxWidth()){
            items(1){
                Text(text = "Type: $type", modifier = Modifier.padding(10.dp,4.dp))
                Text(text = "Name: $name", modifier = Modifier.padding(10.dp,4.dp))
                Text(text = "Street: $street", modifier = Modifier.padding(10.dp,4.dp))
                Text(text = "City: $city", modifier = Modifier.padding(10.dp,4.dp))
                Text(text = "State: $state", modifier = Modifier.padding(10.dp,4.dp))
                Text(text = "PinCode: $pincode", modifier = Modifier.padding(10.dp,4.dp))
                Text(text = "Phone: $phone", modifier = Modifier.padding(10.dp,4.dp))
            }
        }



    }

}

@Composable
fun BillingProductList(
    products:List<CartProduct>?
) {
    val listState = rememberLazyListState()
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
        ){
            if(products !=null){
                items(count = products.size,
                    key = {
                        UUID.randomUUID()
//                        products[it].product.id
                    },
                    itemContent = { index ->
                        val product = products[index]
                        EachBillingProduct(cartProduct = product)
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                )
            }
        }
    }

}

@Composable
fun EachBillingProduct(
    cartProduct: CartProduct
) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){
        Row (modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cartProduct.product.images[0])
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_kleine_shape),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(120.dp)
                    .padding(5.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),

                )
            Column(Modifier.fillMaxHeight()) {
                Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {

                    Text(text = cartProduct.product.name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
                    Text(text = "Price: Rs. ${cartProduct.product.price} ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
                    Text(text = "Quantity:  ${cartProduct.quantity}")

                }



            }


        }
    }

}
//fun printNavBackStack(navController: NavController) {
//    var currentEntry = navController.currentBackStackEntry
//    while (currentEntry != null) {
//        Log.d("NavBackStack", "Route: ${currentEntry.destination.route}")
//        Log.d("NavBackStack", "Arguments: ${currentEntry.arguments}")
//        currentEntry = currentEntry.previous
//    }
//}

@Composable
fun PrintBackStack(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Print the back stack entries to log
    navBackStackEntry?.let { entry ->
        Log.d("NavigationBackStack", "BackStack Entry: ${entry.destination.route}")

        var parentBackStackEntry = entry.destination.route?.let { navController.getBackStackEntry(it) }
        while (parentBackStackEntry != null) {
            Log.d("NavigationBackStack", "Parent Entry: ${parentBackStackEntry.destination.route}")
            parentBackStackEntry = parentBackStackEntry.destination.route?.let {
                navController.getBackStackEntry(
                    it
                )
            }
        }
    }

    // Rest of your composable
}

