package com.example.furniture_app.screens.loginscreens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.RowScopeInstance.weight
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.furniture_app.R

@Composable
fun LoginScreen(navController: NavHostController) {
    Box(modifier =Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.blury_background),
            contentDescription = "",
            modifier =Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(300.dp))
            Text(
                text = "The Right Address",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "For Shopping",
                fontSize = 32.sp, fontWeight = FontWeight.Bold
            )
            Text(
                text = "Anyday",
                fontSize = 32.sp, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.start_desc),
                fontSize = 20.sp, fontWeight = FontWeight.Normal,



                modifier = Modifier.padding(25.dp,0.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
                navController.navigate("Main_App"){
                    popUpTo("Login_Screen"){
                        inclusive=true}
                launchSingleTop=true
                restoreState=true

                }
                             },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.size(150.dp,50.dp)

            ) {
                Text(text = "Register", fontSize = 20.sp)

            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.navigate("Login_Details_Screen") },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.size(150.dp,50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Login", fontSize = 20.sp,)

            }
            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(rememberNavController())
}
