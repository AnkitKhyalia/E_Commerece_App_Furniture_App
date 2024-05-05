package com.example.furniture_app.screens.appscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.data.EmptyGroup.name
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.furniture_app.R
import com.example.furniture_app.screens.checkoutscreens.options
import com.example.furniture_app.util.Header
import com.example.furniture_app.util.Resource
import com.example.furniture_app.viewmodels.mainapp.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
){
    val user = profileViewModel.user.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            Header(onClick = {navController.popBackStack()}, heading ="Profile" )
            when(user){
                is Resource.Loading ->{
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxSize()
                        .size(100.dp))
                }

                is Resource.Error -> {
                    Text(text = user.message.toString())
                }
                is Resource.Success -> {
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp))
                    {
                        userRowCard(OnClick = {navController.navigate("User_Details_Screen")},name =user.data!!.firstName, image2 = user.data.imagePath)
                        Spacer(modifier = Modifier.height(20.dp))
                        RowCard(imageVector = Icons.Default.LocationOn, option ="Orders" , onClick = {navController.navigate("Order_Details_Screen")})
                        Spacer(modifier = Modifier.height(20.dp))
                        RowCard(imageVector = Icons.Default.Notifications, option ="Notifications", onClick = {} )
                        Spacer(modifier = Modifier.weight(1f))
                        RowCard(imageVector = Icons.Default.Close, option ="Logout", onClick = {
                            profileViewModel.logout()
                            navController.navigate("Login_Screen") {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } )
                        Spacer(modifier = Modifier.weight(1f))

                    }

                }
                is Resource.Unspecified -> {

                }
            }
        }



    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userRowCard(
    OnClick:()-> Unit,
    name:String,
     image2:String?
) {
    var image:String? = null
    if(image2 !=""){
        image = image2
    }
    Card(
        onClick = OnClick,
        modifier = Modifier
            .fillMaxWidth()

            .height(80.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_kleine_shape),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .clip(shape = RoundedCornerShape(10.dp)),

                )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = name)
//            Spacer(modifier = Modifier.weight(1f))


        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowCard(
    onClick:() ->Unit,
    imageVector: ImageVector,
    option:String
) {
    var checked = remember {
        mutableStateOf(false)
    }
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Image(imageVector = imageVector, contentDescription ="" )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = option)
            Spacer(modifier = Modifier.weight(1f))

//            Switch(checked = checked.value, onCheckedChange ={checked.value = !checked.value} )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun RowCardPreview() {
//    ProfileScreen()
//    userRowCard(name = "ankit", image ="" )
//    RowCard(imageVector = Icons.Default.Notifications, option ="Notifications" )

}