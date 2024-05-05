package com.example.furniture_app.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.furniture_app.screens.appscreens.HomeScreen
import com.example.furniture_app.screens.appscreens.OrderDetailsScreen
import com.example.furniture_app.screens.appscreens.ProductDetailsScreen
import com.example.furniture_app.screens.appscreens.ProfileScreen
import com.example.furniture_app.screens.appscreens.SearchScreen
import com.example.furniture_app.screens.appscreens.ShoppingCartScreen
import com.example.furniture_app.screens.appscreens.UserDetailsScreen
import com.example.furniture_app.viewmodels.mainapp.MainCategoryViewModel
import com.example.furniture_app.viewmodels.mainapp.ProfileViewModel


@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.homeNavGraph(navcontroller: NavController){
    navigation(startDestination ="Home_Screen" ,route="Main_App"){
        composable("Home_Screen"){
            HomeScreen(navcontroller)
        }
        composable("Search_Screen"){
            SearchScreen(navcontroller)
        }
        composable("Shopping_Cart_Screen"){backStackEntry->
            val parentEntry = remember {
                navcontroller.getBackStackEntry("Home_Screen")
            }
            val parentViewModel = hiltViewModel<MainCategoryViewModel>(
                parentEntry
            )
            ShoppingCartScreen(navcontroller,parentViewModel)
        }
        profileNavigationGraph(navcontroller)
        composable("Product_Navigation_Graph"){backStackEntry->
//                val parentViewModel = backStackEntry.sharedViewModel<MainCategoryViewModel>(navController = navcontroller)
            val parentEntry = remember {
                navcontroller.getBackStackEntry("Home_Screen")
            }
            val parentViewModel = hiltViewModel<MainCategoryViewModel>(
                parentEntry
            )
//            ProductNavigationGraph( mainCategoryViewModel = parentViewModel )
            ProductDetailsScreen(navController = navcontroller, mainCategoryViewModel = parentViewModel)
        }
        CheckoutNavigationGraph(navcontroller)
    }
}
@SuppressLint("SuspiciousIndentation", "UnrememberedGetBackStackEntry")
@Composable
fun HomeNavGraph(navcontroller:NavHostController){
//    val viewmodel: MainCategoryViewModel = hiltViewModel()
            NavHost(navController = navcontroller, startDestination = "Home_Screen" ){
        composable("Home_Screen"){
            HomeScreen(navcontroller)
        }
        composable("Search_Screen"){
            SearchScreen(navcontroller)
        }
        composable("Shopping_Cart_Screen"){backStackEntry->
            val parentEntry = remember {
                navcontroller.getBackStackEntry("Home_Screen")
            }
            val parentViewModel = hiltViewModel<MainCategoryViewModel>(
                parentEntry
            )
            ShoppingCartScreen(navcontroller,parentViewModel)
        }
        profileNavigationGraph(navcontroller)
//        composable("Profile_Screen"){
//            ProfileScreen()
//        }
        composable("Product_Navigation_Graph"){backStackEntry->
//                val parentViewModel = backStackEntry.sharedViewModel<MainCategoryViewModel>(navController = navcontroller)
            val parentEntry = remember {
                navcontroller.getBackStackEntry("Home_Screen")
            }
            val parentViewModel = hiltViewModel<MainCategoryViewModel>(
                parentEntry
            )
                    ProductNavigationGraph( mainCategoryViewModel = parentViewModel )
        }
                CheckoutNavigationGraph(navcontroller)
//        composable("Checkout_Navigation_Graph"){
//            CheckoutNavigationGraph()
//        }
//            composable("User_Details_Screen"){
//
//            }


    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.profileNavigationGraph(navController: NavController){
    navigation(startDestination = "Profile_Screen",route="Profile_Navigation_Graph"){
        composable("Profile_Screen"){
            ProfileScreen(navController)
        }

        composable("User_Details_Screen"){backStackEntry->
            val parentEntry = remember {
                navController.getBackStackEntry("Profile_Screen")
            }
            val parentViewModel = hiltViewModel<ProfileViewModel>(
                parentEntry
            )
            UserDetailsScreen(navController,profileViewModel = parentViewModel)
        }
        composable("Order_Details_Screen"){
            OrderDetailsScreen(navController)
        }

    }
}