package com.example.furniture_app.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Header(
    onClick :() ->Unit,
    heading:String) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(0.dp,5.dp,0.dp,10.dp),
        ) {
        Row (
            Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription ="",Modifier.size(50.dp) )

            }
            Spacer(modifier = Modifier.weight(0.7f))
            Text(text = heading, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(
        {},
        heading ="User Details" )
}