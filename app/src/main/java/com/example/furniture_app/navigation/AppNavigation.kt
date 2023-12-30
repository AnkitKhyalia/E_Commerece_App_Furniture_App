package com.example.furniture_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.furniture_app.screens.appscreens.AddProductScreen
import com.example.furniture_app.screens.appscreens.BottomNavGraph
import com.example.furniture_app.screens.loginscreens.LoginDetailsScreen
import com.example.furniture_app.screens.loginscreens.LoginScreen
import com.example.furniture_app.screens.loginscreens.RegisterScreen
import com.example.furniture_app.screens.loginscreens.StartScreen



@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "Start_Screen" ){
        composable("Start_Screen"){
            StartScreen(navController)
        }
        composable("Login_Screen"){
            LoginScreen(navController)
        }
        composable("Login_Details_Screen"){
            LoginDetailsScreen(navController)
        }
        composable("Register_Screen"){
            RegisterScreen(navController)
        }
        composable("Main_App"){
            BottomNavGraph()

        }
        composable("Add_Product_Screen"){
            AddProductScreen()
        }
    }
}

