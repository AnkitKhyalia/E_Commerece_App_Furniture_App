package com.example.furniture_app.screens.appscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.furniture_app.data.Address
import com.example.furniture_app.data.Order
import com.example.furniture_app.screens.checkoutscreens.EachAddress
import com.example.furniture_app.util.Header
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.mainapp.OrderViewModel
import com.google.android.play.integrity.internal.f

@Composable
fun OrderDetailsScreen(
    navController: NavController,
    orderViewModel: OrderViewModel = hiltViewModel()

) {

    val allorderlist = orderViewModel.order.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Header(onClick = {navController.popBackStack()}, heading ="All Orders" )
        when(allorderlist.value){
            is Resource.Error ->{
                Text(text = allorderlist.value.message.toString())
            }
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .size(100.dp))
            }
            is Resource.Success -> {
                allorderlist.value.data?.let {
                    OrderList(allOrderList = allorderlist.value.data!!)
                }
            }
            is Resource.Unspecified -> {

            }
        }
    }


}

@Composable
fun OrderList(allOrderList:List<Order>) {
    val liststate = rememberLazyListState()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            LazyColumn(modifier = Modifier.padding(10.dp), state = liststate){
                items(count = allOrderList.size){index ->
                    val order = allOrderList[index]
                    EachOrder(order)
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
        }

    }

}
@Composable
fun EachOrder(order: Order) {

    Card(modifier = Modifier.fillMaxWidth()) {
        Text(text ="Status: ${order.orderstatus}" )

        Text(text = order.address.toString())
        Column(modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(8.dp)
        ){
//            val n= order.products.size
            Divider()
            for(cartProduct in order.products){
//                val cartProduct = order.products[index]
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(text = cartProduct.product.name)
                    Text(text ="Price: "+ cartProduct.product.price)
                    Text(text = "Qty: "+cartProduct.quantity.toString())

                }
                Divider()
            }
        }
        Text(text ="Total Price: "+ order.totalPrice.toString(), fontWeight = FontWeight.Bold)

    }
}



@Preview
@Composable
fun EachOrderPreview() {
    EachOrder(order = Order("Confirmed",100, emptyList(), Address()))
    
}