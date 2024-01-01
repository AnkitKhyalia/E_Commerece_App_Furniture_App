package com.example.furniture_app.screens.appscreens

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.furniture_app.R
import com.example.furniture_app.data.CartProduct
import com.example.furniture_app.firebase.FirebaseCommon
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.CartViewModel
import com.example.furniture_app.viewmodels.MainCategoryViewModel
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ShoppingCartScreen(
    navController: NavHostController,
    mainCategoryViewModel: MainCategoryViewModel,
    cartViewModel: CartViewModel = hiltViewModel()
){
    val allcartproductslist = cartViewModel.allCartProducts.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxWidth()){

                if (allcartproductslist.value is Resource.Loading) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize().padding(40.dp))
                } else if (allcartproductslist.value is Resource.Success) {
                    val productlist = (allcartproductslist.value as Resource.Success).data
                    ProductList(products = productlist,navController,mainCategoryViewModel,cartViewModel.totalprice.value.toString(),cartViewModel)
                } else if (allcartproductslist.value is Resource.Error) {
                    (allcartproductslist.value as Resource.Error).message?.let { Text(it) }
                }
            }
        }

    }
}


@Composable
fun ProductList(
    products:List<CartProduct>?,
    navController: NavController,
    mainCategoryViewModel: MainCategoryViewModel,
    totalprice:String,
    cartViewModel: CartViewModel
){  
    Column(modifier = Modifier.fillMaxWidth()) {


        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
//        state = listState,
        ) {

            if (products != null) {
                items(count = products.size,
                    key = {
//                    UUID.randomUUID()
                        products[it].product.id
                    },
                    itemContent = { index ->
                        val product = products[index]
                        EachProduct(
                            products[index],
                            navController = navController,
                            index = index,
                            type = "x",
                            mainCategoryViewModel = mainCategoryViewModel,
                            cartViewModel = cartViewModel
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                )
            }
        }
        Column(modifier = Modifier.fillMaxWidth()) {


            Text(text = "total amount: $totalprice")
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Proceed to Checkout")

            }
        }
    }
    

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachProduct(
    cartProduct: CartProduct,
    navController: NavController,
    index:Int,
    type:String,
    mainCategoryViewModel: MainCategoryViewModel,
    cartViewModel: CartViewModel
){
    var opendialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Card(onClick = {
        mainCategoryViewModel.currentdisplayproduct.value = cartProduct.product
        navController.navigate("Product_Details_Screen/$type/$index")}, modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)) {
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
                Column(Modifier.fillMaxHeight()) {

                    Text(text = cartProduct.product.name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))

//                Text(text ="Category: ${cartProduct.product.category}", modifier = Modifier.padding(2.dp))
                    Text(text = "Price: Rs. ${cartProduct.product.price} ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
//                cartProduct.product.offer.let {
//                    Text(text ="Offer: ${cartProduct.product.offer+"%"}", modifier = Modifier.padding(2.dp))
//                }


                }
                Row(
                    verticalAlignment =Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = { cartViewModel.changeQuantity(cartProduct, FirebaseCommon.QuantityChanging.INCREASE) },

                        modifier = Modifier.size(30.dp),
                        contentPadding = PaddingValues(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Text(
                        text = cartProduct.quantity.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Button(
                        onClick = {
                            if (cartProduct.quantity == 1) {
                                Toast.makeText(context, "Quantity can not be zero", Toast.LENGTH_SHORT).show()
                            } else {
                                cartViewModel.changeQuantity(cartProduct, FirebaseCommon.QuantityChanging.DECREASE)
                            }
                        },
                        modifier = Modifier.size(30.dp),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = "",
                            modifier = Modifier.scale(1.5f)
                        )
                    }
                }


            }




            Column(modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center){
                IconButton(onClick = {
                    opendialog=true
                },
                    modifier = Modifier.fillMaxHeight(),
                    colors = IconButtonDefaults.iconButtonColors(
//                        containerColor = Color.White,
                        contentColor = Color.Red
                    )
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription ="" )
                }
            }

//            Text(text = cartProduct.quantity.toString())

        }

    }
    if(opendialog){
        AlertDialog(
            onDismissRequest = {
                opendialog = false
                },
            confirmButton = {
                Button(onClick = { cartViewModel.deleteitem(cartProduct)}) {
                    Text(text = "Yes")

                }
            },
            dismissButton = {
                Button(onClick = { opendialog=false}) {
                    Text(text = "No")

                }

            },
            title = { Text(text = "Delete Item") },
            text = { Text(text = "Do you want to remove this product from Cart") },

            )
    }



}

@Composable
fun buttonscolumn(){

}