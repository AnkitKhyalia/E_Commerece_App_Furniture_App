package com.example.furniture_app.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.furniture_app.screens.appscreens.HomeScreen
import com.example.furniture_app.screens.appscreens.ProductDetailsScreen
import com.example.furniture_app.screens.appscreens.ProfileScreen
import com.example.furniture_app.screens.appscreens.SearchScreen
import com.example.furniture_app.screens.appscreens.ShoppingCartScreen
import com.example.furniture_app.viewmodels.MainCategoryViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeNavGraph(navcontroller:NavHostController){
    val viewmodel:MainCategoryViewModel = hiltViewModel()
            NavHost(navController = navcontroller, startDestination = "Home_Screen" ){
        composable("Home_Screen"){
            HomeScreen(navcontroller,viewmodel)
        }
        composable("Search_Screen"){
            SearchScreen()
        }
        composable("Shopping_Cart_Screen"){
            ShoppingCartScreen(navcontroller,viewmodel)
        }

        composable("Profile_Screen"){
            ProfileScreen()
        }
        composable("Product_Details_Screen/{type}/{index}"){navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getString("index")
                    val type=navBackStackEntry.arguments?.getString("type")
                if(type !=null && index != null){
                    ProductDetailsScreen(type,index,viewmodel)
                }



        }
    }
}