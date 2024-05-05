package com.example.furniture_app.screens.appscreens

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.furniture_app.R
import com.example.furniture_app.util.Header
import com.example.furniture_app.viewmodels.mainapp.ProfileViewModel

@Composable
fun UserDetailsScreen(
    navController: NavController,
    profileViewModel:ProfileViewModel
) {

    val user = profileViewModel.user.collectAsState().value.data

    Surface(modifier = Modifier.fillMaxSize()) {
        Header(onClick = {navController.popBackStack()}, heading ="User Details" )
        if(user !=null) {


            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.imagePath)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_kleine_shape),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(5.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),

                    )
                Divider()
                Spacer(modifier = Modifier.height(20.dp))
//                showinfo(heading = "image path", input =user.imagePath )
                showinfo(heading = "Name", input =user.firstName +user.lastName)


                Spacer(modifier = Modifier.height(20.dp))
                showinfo(heading = "Email", input = user.email)
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showinfo(heading:String,input:String) {
    Card( modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Text(text = "$heading: $input", modifier = Modifier.padding(10.dp,20.dp))
    }
}

@Preview
@Composable
fun textfiledprviem() {
    showinfo(heading = "Name", input = "ankit")
}

