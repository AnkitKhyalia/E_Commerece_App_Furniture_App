package com.example.furniture_app.screens.appscreens

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.furniture_app.navigation.HomeNavGraph
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking


sealed class BottomNavigationItem(
    val route:String,
    val title:String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
){
    object Home:BottomNavigationItem(
        "Home_Screen",
        "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home
    )
    object Search:BottomNavigationItem(
        "Search_Screen",
        "Search",
        Icons.Filled.Search,
        Icons.Outlined.Search
    )
    object ShoppingCart:BottomNavigationItem(
        "Shopping_Cart_Screen",
        "Cart",
        Icons.Filled.ShoppingCart,
        Icons.Outlined.ShoppingCart
    )
    object Profile:BottomNavigationItem(
        "Profile_Screen",
        "Profile",
        Icons.Filled.Person,
        Icons.Outlined.Person
    )

}
val items = listOf(
    BottomNavigationItem.Home,
    BottomNavigationItem.Search,
    BottomNavigationItem.ShoppingCart,
    BottomNavigationItem.Profile
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraph(){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination
                items.forEachIndexed{index, bottomNavigationItem ->
                    val isSelected = currentDestination?.route == bottomNavigationItem.route
                    NavigationBarItem(
                        selected =isSelected ,
                        onClick = {
                            if(!isSelected) {

                                navController.navigate(bottomNavigationItem.route)
                                {
//                                popUpTo(navController.graph.findStartDestination().id)
                                    popUpTo("Home_Screen")
                                    {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                        },
                        label = {
                            Text(text = bottomNavigationItem.title)
                        },
                        icon = {
                            Icon(
                                imageVector= if(isSelected){
                                    bottomNavigationItem.selectedIcon
                                }else{
                                    bottomNavigationItem.unselectedIcon
                                     },
                                contentDescription =bottomNavigationItem.title
                            )

                        }
                    )
                }

            }
        }
    ) {innerpadding->
        Box(modifier = Modifier.padding(innerpadding)) {

            HomeNavGraph(navcontroller = navController)
        }
    }
}

