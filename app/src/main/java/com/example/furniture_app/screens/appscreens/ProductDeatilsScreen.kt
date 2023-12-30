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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.furniture_app.data.Product
import com.example.furniture_app.viewmodels.MainCategoryViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProductDetailsScreen(type:String,
                         index: String,
                         mainCategoryViewModel:MainCategoryViewModel = hiltViewModel()

) {

    val product :Product?
    if (
        type == "Main"

    ){
        product = mainCategoryViewModel.allproducts.value.data?.get(index.toInt())

    }
    else{
        product = mainCategoryViewModel.productsByCategory.value.data?.get(index.toInt())


    }
    if(product !=null){
        ProductComposable(product = product)

    }
    else{
        Text(text = "No data Available")
    }
    

}

@Composable
fun ProductComposable(
    
    product: Product
){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
//            .padding(10.dp)
        ) {

            val listState = rememberLazyListState()

            LazyRow(state = listState) {
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
                        .fillMaxWidth(0.9f)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(20.dp))

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
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Add to Bag")

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
                        .size(10.dp)
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