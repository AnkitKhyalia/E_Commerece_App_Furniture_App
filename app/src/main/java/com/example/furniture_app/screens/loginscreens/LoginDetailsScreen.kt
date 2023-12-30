package com.example.furniture_app.screens.loginscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.furniture_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginDetailsScreen(navController: NavHostController) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.blury_background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(300.dp))
            Text(
                text = "Let's Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )


            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Have an account? Login",
                fontSize = 20.sp, fontWeight = FontWeight.Normal,



                modifier = Modifier.padding(25.dp,0.dp),
                textAlign = TextAlign.Left,
                style = TextStyle(color = Color.Gray)
            )

            OutlinedTextField(value = email, onValueChange ={email=it},
                label = { Text(text = "Email")})
            OutlinedTextField(value = password, onValueChange ={password= it},
                label = { Text(text = "Password")})


            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth(0.73f)

            ) {
                Text(text = "Login", fontSize = 20.sp)

            }
            Row (modifier= Modifier.fillMaxWidth(0.73f),
                horizontalArrangement = Arrangement.SpaceBetween){
                Button(onClick = { /*TODO*/ },
                    shape= RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor= Color.Gray,
                        contentColor = Color.White)
                ) {
                    Text(text = "Facebook")
                }

                Button(onClick = { /*TODO*/ },
                    shape= RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor=Color.Gray,
                        contentColor = Color.White)
                ) {
                    Text(text = "Google")
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}

@Composable
@Preview
fun LoginDetailsScreenPreview(){
    LoginDetailsScreen(rememberNavController())
}