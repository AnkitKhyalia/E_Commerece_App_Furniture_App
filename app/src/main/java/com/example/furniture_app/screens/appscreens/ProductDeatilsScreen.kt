package com.example.furniture_app.screens.appscreens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.furniture_app.R
import com.example.furniture_app.data.CartProduct
import com.example.furniture_app.data.Product
import com.example.furniture_app.viewmodels.MainCategoryViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProductDetailsScreen(type:String,
                         index: String,
                         mainCategoryViewModel:MainCategoryViewModel = hiltViewModel()

) {

    var product :Product?

    product = mainCategoryViewModel.currentdisplayproduct.value
    ProductComposable(product = product,mainCategoryViewModel)
    

}

@Composable
fun ProductComposable(
    
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
//            .padding(10.dp)
        ) {
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
            DotsIndicator(totalDots = product.images.size, selectedIndex =listState.firstVisibleItemIndex, selectedColor = Color.Red , unSelectedColor = Color.Gray)
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
                horizontalArrangement = Arrangement.End
            ) {

                Text(text = "Quantity :")
                Spacer(modifier = Modifier.width(10.dp))

                IconButton(onClick = { quantity = 1 }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription ="")

                }

                Card(
                    shape = RoundedCornerShape(2.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )

                ) {
                    Text(text = quantity.toString())
                }

                IconButton(onClick = { quantity ++ }) {
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
            Button(onClick = { /*TODO*/
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