package com.example.furniture_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.furniture_app.screens.appscreens.ProductDetailsScreen
import com.example.furniture_app.viewmodels.mainapp.MainCategoryViewModel


@Composable
fun ProductNavigationGraph(
    mainCategoryViewModel: MainCategoryViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Product_Details_Screen" ){
        composable("Product_Details_Screen"){
            ProductDetailsScreen(navController,mainCategoryViewModel)
        }
    }

}