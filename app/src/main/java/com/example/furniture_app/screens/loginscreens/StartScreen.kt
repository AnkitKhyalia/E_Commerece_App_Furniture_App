package com.example.furniture_app.screens.loginscreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.furniture_app.R

@Composable
fun StartScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.TopStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.texture_left),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.TopStart)


            )
//        Image(painter = painterResource(id = R.drawable.ellips),
//            contentDescription ="",
//            modifier = Modifier
//                .wrapContentSize(align = Alignment.Center)
//
//            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(300.dp)
                        .scale(1f)
                ) {
                    drawCircle(color = Color(0xFF33A5DDFE))
                }
            }
            Image(
                painter = painterResource(id = R.drawable.texture_right),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.BottomEnd)


            )
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { navController.navigate("Login_Screen"){
                    popUpTo("Start_Screen") {
                        inclusive = true
                    }
                } },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.scale(1.5f)

                ) {
                    Text(text = "Start")
                    
                }
                Spacer(modifier = Modifier.height(100.dp))
            }


//        Image(imageVector = R.drawable.texture_left, contentDescription = )
        }
    }
}
@Preview
@Composable
fun previewStartScreen(){

    StartScreen(rememberNavController())
}

