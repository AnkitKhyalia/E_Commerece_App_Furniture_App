package com.example.furniture_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun AppNavigationGraph(
    firebaseAuth: FirebaseAuth,
    navController: NavHostController
) {

    val currentuser = Firebase.auth.currentUser
//    val navController = rememberNavController()
    var startdesti ="Start_Screen"
    if(currentuser !=null){
        startdesti = "Main_App"
    }
    NavHost(navController = navController, startDestination = startdesti ){
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
//        composable("Main_App"){
//
//            BottomNavGraph()
//
//        }
        homeNavGraph(navController)
        composable("Add_Product_Screen"){
            AddProductScreen()
        }

    }
}

