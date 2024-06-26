package com.example.furniture_app.screens.appscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
//import androidx.compose.material.TabRowDefaults.*
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.furniture_app.R
import com.example.furniture_app.data.Product
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.mainapp.MainCategoryViewModel
import java.util.UUID


val tablist:List<String> = listOf("Main","Chair","Table","Bed","Bench")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController:NavController,
    mainCategoryViewModel: MainCategoryViewModel = hiltViewModel()
){
    val coroutineScope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableStateOf(0) }
//    val allproducts = mainCategoryViewModel.allproducts.collectAsState(initial = Resource.Loading())
    val allproducts = mainCategoryViewModel.allproducts.collectAsState(initial = Resource.Loading())
    val productsByCategory = mainCategoryViewModel.productsByCategory.collectAsState(initial = Resource.Loading())
    val noMoreProduct = mainCategoryViewModel.noMoreProducts.collectAsState().value

    Surface (modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxWidth()){

            ScrollableTabRow(selectedTabIndex =selectedTabIndex , indicator = {tabPositions ->
                TabRowDefaults.Indicator(

                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp),



            ) {
                tablist.forEachIndexed { index, tabName ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            // Handle tab selection
                            coroutineScope.launch {
                                selectedTabIndex = index
                                if(tabName != "Main") {
                                    mainCategoryViewModel.GetProductsByCategories(tabName)
                                }
                            }
                        }
                    ) {
                        Text(tabName)

                    }
                }
            }

            if(selectedTabIndex==0) {
                if (allproducts.value is Resource.Loading) {
                    CircularProgressIndicator()
                } else if (allproducts.value is Resource.Success) {
                    val productlist = (allproducts.value as Resource.Success).data
                    ProductsList(products = productlist, tablist[selectedTabIndex],navController,"Main", mainCategoryViewModel,noMoreProduct)
                } else if (allproducts.value is Resource.Error) {
                    (allproducts.value as Resource.Error).message?.let { Text(it) }
                }
//            EachProduct(Name = "Chair", Category ="Chair" , Price =500f , Offer =10f , Image =Icons.Default.Email )
            }
            else{
                if (productsByCategory.value is Resource.Loading) {
                    CircularProgressIndicator()
                } else if (productsByCategory.value is Resource.Success) {
                    val productlist = (productsByCategory.value as Resource.Success).data
                    ProductsList(products = productlist, tablist[selectedTabIndex], navController,"categories", mainCategoryViewModel,noMoreProduct)
                } else if (productsByCategory.value is Resource.Error) {
                    (productsByCategory.value as Resource.Error).message?.let { Text(it) }
                }

            }

        }

    }
}

@Composable
fun ProductsList(products: List<Product>?,
                 tabName:String,
                 navController: NavController,
                 type:String,
                 mainCategoryViewModel: MainCategoryViewModel,
                 noMoreProducts:Boolean) {

    val listState= rememberLazyListState()
//    LazyColumn(
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        state = listState,
//    ) {
//        if (products != null) {
//            items(count = products.size) { index ->
//                val product = products[index]
//                EachProduct(
//                    Name = product.name,
//                    Category = product.category,
//                    Price = product.price,
//                    Offer = product.offer,
//                    Image = product.images[0]
//                )
//                Spacer(modifier = Modifier.height(5.dp))
//            }
//
//            // Add a button at the end
//            item {
//                Spacer(modifier = Modifier.height(16.dp)) // Optional: Add some space between the last item and the button
//                Button(
//                    onClick = {
//                        mainCategoryViewModel.loadMoreAllProducts(tabName)
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                ) {
//                    Text("Load More")
//                }
//
//            }
//        }
//    }
//    var x by remember{mutableStateOf("1")}
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
//        state = listState,
    ) {

        if (products != null) {
            items(count = products.size+1,
                key = {
                    UUID.randomUUID()
//                    products[it].id
                },
                itemContent = {index->
                    if(index<products.size) {
                        val product = products[index]
                        EachProduct(
                            Name = product.name,
                            Category = product.category,
                            Price = product.price,
                            Offer = product.offer,
                            Image = product.images[0],
                            navController = navController,
                            index = index,
                            type= if(type !="Main"){
                                product.category
                            }
                            else{
                                type
                            },
                            mainCategoryViewModel,
                            product

                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    else{
                        if(noMoreProducts){
                            Text(text = "No More Products Are Available")
                        }
                        else{
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
//                                isLoadingMore = true
                                    mainCategoryViewModel.loadMoreAllProducts(tabName) // Function to fetch more data
                                }
                            ) {
                                Text("Load More")
                            }
                        }

                    }
                }
            )


        }
        else{
            item {
                Text(text = "No More Products to Display")
            }
        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachProduct(
    Name:String,
    Category:String,
    Price:String,
    Offer:String?,
    Image:String?,
    navController: NavController,
    index:Int,
    type:String,
    mainCategoryViewModel: MainCategoryViewModel,
    product: Product
){

    Card(onClick = {
        mainCategoryViewModel.currentdisplayproduct.value = product
        navController.navigate("Product_Navigation_Graph")
//        navController.navigate(route = "Product_Navigation_Graph/Product_Details_Screen")

    }, modifier = Modifier

        .fillMaxWidth()
        .padding(5.dp)) {
        Row (modifier = Modifier.fillMaxWidth()){
//            val painter = // Optional: Add image transformations
//                rememberAsyncImagePainter(
//                   model= ImageRequest.Builder(LocalContext.current)
//                        .data(data = Image)
//                    .build()
//                )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Image)
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
//            Image(
//                painter = painter,
//                contentDescription = Name, // Accessibility for screen readers
//                modifier = Modifier.size(120.dp)
//            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = Name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))

                Text(text ="Category: $Category", modifier = Modifier.padding(2.dp))
                Text(text = "Price: Rs. ${Price.toString()} ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
                Offer.let {
                    Text(text ="Offer: ${Offer.toString()+"%"}", modifier = Modifier.padding(2.dp))
                }

            }

        }

    }



}
//@Preview
//@Composable
//fun MainScreenPreview(){
//    HomeScreen()
//}




