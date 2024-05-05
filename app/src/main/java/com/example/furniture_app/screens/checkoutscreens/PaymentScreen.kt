package com.example.furniture_app.screens.checkoutscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.furniture_app.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaymentScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(painter = painterResource(id = R.drawable.blury_background), contentDescription ="" )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart){
            Text(text = "Currently We do not accept any Virtual Payment,Please Pay when you Receive the order", modifier = Modifier
                .basicMarquee()
                .padding(20.dp), fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Place Order")
            }
        }


    }
}