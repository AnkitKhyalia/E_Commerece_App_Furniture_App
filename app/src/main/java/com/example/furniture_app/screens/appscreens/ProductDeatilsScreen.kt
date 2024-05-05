package com.example.furniture_app.screens.appscreens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.furniture_app.data.Product
import com.example.furniture_app.util.Header
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.mainapp.MainCategoryViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    mainCategoryViewModel: MainCategoryViewModel = hiltViewModel()

) {

    var product :Product?
    val addtocart = mainCategoryViewModel.addToCart.collectAsState().value
   val context = LocalContext.current
    product = mainCategoryViewModel.currentdisplayproduct.value
    ProductComposable(navController,product = product,mainCategoryViewModel)
    
    when(addtocart){
        is Resource.Error -> {
            LaunchedEffect(key1 = Unit) {
                Toast.makeText(
                    context,
                    addtocart.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        is Resource.Loading -> {
            CircularProgressIndicator(modifier = Modifier.size(60.dp))
        }
        is Resource.Success -> {
            LaunchedEffect(key1 = Unit) {
                Toast.makeText(
                    context,
                    "Item Added Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        is Resource.Unspecified -> {

        }
    }
}

@Composable
fun ProductComposable(
    navController:NavController,
    product: Product,
    mainCategoryViewModel: MainCategoryViewModel
){
    var isfav by remember { mutableStateOf(false) }
    var quantity by remember {
        mutableStateOf(1)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
        ) {
            Header(onClick = {navController.popBackStack()}, heading ="Product Information" )
            val listState = rememberLazyListState()
            LazyRow(state = listState, modifier = Modifier.fillMaxWidth()) {
            items(product.images.size) { index ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.images[index])
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_kleine_shape),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
//                        .fillMaxWidth(0.2f)
                        .height(250.dp)
                        .width(250.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(20.dp))
//                        .fillMaxWidth()

                )

                }
            }
//            DotsIndicator(totalDots = product.images.size, selectedIndex =listState.firstVisibleItemIndex, selectedColor = Color.Red , unSelectedColor = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = product.name)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = product.price)
                if(product.offer!=null){
                    Text(text =product.offer )
                }
                else{
                    Text(text = "No offer")
                }

            }

            Text(text = "Description", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = product.description)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Quantity :")

                Spacer(modifier = Modifier.width(10.dp))

                Button(onClick = { quantity =1 },
                    modifier = Modifier.size(30.dp), contentPadding = PaddingValues(5.dp)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription ="")

                }
                Spacer(modifier = Modifier.width(10.dp))

                Card(
                    shape = RoundedCornerShape(2.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp,

                    ),
                    modifier = Modifier.size(30.dp),

                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = quantity.toString())
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = { quantity ++ }, modifier = Modifier.size(30.dp), contentPadding = PaddingValues(5.dp)) {
                    Icon(imageVector = Icons.Default.Add, contentDescription ="")

                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = {mainCategoryViewModel.addUpdateProductInCart(CartProduct(product, quantity =quantity )) },
                modifier = Modifier.fillMaxWidth(0.6f)) {

                Icon(imageVector = Icons.Default.ShoppingCart,  contentDescription ="" )
                Text(text = "Add to Cart")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = {
                                 isfav = !isfav},
                modifier = Modifier.fillMaxWidth()
                    ) {
                Icon(
                    imageVector = if (isfav) {
                                        Icons.Filled.Favorite
                    }else{Icons.Outlined.FavoriteBorder}, contentDescription = ""
                )
            }
        }
    }

}
@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color,
    unSelectedColor: Color,
){

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

//            .wrapContentWidth()
//            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}
//@Preview
//@Composable
//fun PreviewProductComposable(){
//    ProductComposable(product = )
//}