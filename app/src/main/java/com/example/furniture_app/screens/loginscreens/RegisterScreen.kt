package com.example.furniture_app.screens.loginscreens

//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.furniture_app.R
import com.example.furniture_app.data.User
import com.example.furniture_app.util.RegisterFieldsState
import com.example.furniture_app.util.RegisterValidation
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.RegisterViewModel


//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel= hiltViewModel()
) {
  val context = LocalContext.current

//    val context: Context = getApplicationContext()

    var firstname by remember {
        mutableStateOf("")
    }
    var lastname by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val errorstates by registerViewModel.validation.collectAsState(initial = RegisterFieldsState())
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = R.drawable.blury_background),
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
                text = "Let's Register",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )


            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Do You Have an account? Login",
                fontSize = 20.sp, fontWeight = FontWeight.Normal,



                modifier = Modifier.padding(25.dp,0.dp).clickable { navController.navigate("Login_Screen") },
                textAlign = TextAlign.Left,
                style = TextStyle(color = Color.Gray)
            )
            OutlinedTextField(value = firstname, onValueChange ={firstname=it
                                                                errorstates.firstname= RegisterValidation.Success},
                label = { Text(text = "First Name")},
                isError = (errorstates.firstname != RegisterValidation.Success),
                supportingText = {
                    if((errorstates.firstname != RegisterValidation.Success)){
                        Text(text = "First Name can not be empty")
                    }
                }

            )
            OutlinedTextField(value = lastname, onValueChange ={lastname=it },
                label = { Text(text = "Last Name")})
            OutlinedTextField(value = email, onValueChange ={email=it
                errorstates.email= RegisterValidation.Success},
                label = { Text(text = "Email")},
                isError = (errorstates.email != RegisterValidation.Success),
                supportingText = {
                    if((errorstates.email != RegisterValidation.Success)){
                        Text(text = "Email Format is not Correct")
                    }
                })
            OutlinedTextField(value = password, onValueChange ={password =it
                errorstates.password= RegisterValidation.Success},
                label = { Text(text = "Password")},
                isError = (errorstates.password != RegisterValidation.Success),
                supportingText = {
                    if((errorstates.password != RegisterValidation.Success)){
                        Text(text = "Password length must be greater than 5")
                    }
                })


            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {

                registerViewModel.createAccountWithEmailAndPassword(user=User(firstname,lastname,email),password)
//                navController.navigate("Login_Screen")
//                {
//                    popUpTo("Register_Screen") {
//                        inclusive = true
//                    }
//                }
//                firstname=""
//                lastname=""
//                email=""
//                password=""
                             },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth(0.73f)

            ) {
                Text(text = "Register", fontSize = 20.sp)

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

    LaunchedEffect(registerViewModel){
        registerViewModel.register.collect(){result->
            when(result){
                is Resource.Loading -> {
                    Log.d("registercheck","is in loading state")
                }
                is Resource.Success -> {
//                    Toast. makeText(context, "This a simple toast tutorial!", Toast. LENGTH_LONG). show() })
                    Toast.makeText(context,"User added successfully",Toast.LENGTH_SHORT).show()

                    Log.d("registercheck","is in success state")
                    navController.navigate("Login_Screen"){
                        popUpTo("Register_Screen"){
                            inclusive= true
                        }
                    }
                }
                is Resource.Error ->{
                    Log.d("registercheck",result.message.toString())
                }
                else -> Unit

            }

        }
    }

}

@Composable
@Preview
fun RegisterScreenPreview(){
    RegisterScreen(rememberNavController())
}