package com.example.furniture_app.screens.appscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.furniture_app.util.Header

@Composable
fun SearchScreen(navController:NavController){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth()) {
            Header(onClick = {navController.popBackStack()}, heading ="Search" )
            Text(text = "Search Screen")
        }

    }
}