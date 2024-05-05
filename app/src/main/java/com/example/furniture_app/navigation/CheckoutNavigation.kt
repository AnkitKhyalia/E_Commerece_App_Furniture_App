package com.example.furniture_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.furniture_app.data.Address
import com.example.furniture_app.screens.checkoutscreens.AddNewAddressScreen
import com.example.furniture_app.screens.checkoutscreens.AddressDetailsScreen
import com.example.furniture_app.screens.checkoutscreens.BillingScreen
import com.example.furniture_app.screens.checkoutscreens.PaymentScreen
import com.example.furniture_app.viewmodels.mainapp.ProfileViewModel

//@Composable
//fun CheckoutNavigationGraph() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "Address_Details_Screen" ){
//
//        composable("Address_Details_Screen"){
//                AddressDetailsScreen(navController = navController)
//        }
//        composable("Add_New_Address_Screen"){
//        AddNewAddressScreen(navController = navController)
//        }
//        composable("Payment_Screen"){
//        PaymentScreen(navController)
//        }
//        composable("Billing_Screen"){
//            var address= navController.previousBackStackEntry?.savedStateHandle?.get<Address>("address")
//
//
//            BillingScreen(navController,address)
//        }
//    }
//}

fun NavGraphBuilder.CheckoutNavigationGraph(navController: NavController){

    navigation(startDestination ="Address_Details_Screen" , route = "Checkout_Navigation_Graph"){

        composable("Address_Details_Screen"){
            AddressDetailsScreen(navController = navController)
        }
        composable("Add_New_Address_Screen"){
            AddNewAddressScreen(navController = navController)
        }
        composable("Payment_Screen"){
            PaymentScreen(navController)
        }
        composable("Billing_Screen"){
            var address= navController.previousBackStackEntry?.savedStateHandle?.get<Address>("address")

            BillingScreen(navController,address)
        }
    }
}
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry  = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}