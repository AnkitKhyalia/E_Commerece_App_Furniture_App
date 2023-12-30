package com.example.furniture_app.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.furniture_app.navigation.AppNavigationGraph
import com.example.furniture_app.screens.appscreens.AddProductScreen
import com.example.furniture_app.screens.appscreens.HomeScreen
import com.example.furniture_app.ui.theme.Furniture_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Furniture_AppTheme {
                // A surface container using the 'background' color from the theme
                AppNavigationGraph()

//                AddProductScreen()
//                HomeScreen()
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Furniture_AppTheme {
        Greeting("Android")
    }
}