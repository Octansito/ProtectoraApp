package com.example.protectora.ui.auth.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomButton(
    modifier: Modifier,
    painter: Painter,
    title:String){

    Box(modifier=Modifier
        .fillMaxWidth()
        .height(48.dp)
        .padding(horizontal = 32.dp)
        .background(Color.White, CircleShape)
        .border(4.dp, Color.Black, CircleShape),
        contentAlignment =Alignment.CenterStart
    ){
        Image(painter =painter,
            contentDescription = "",
            modifier=Modifier.padding(start = 16.dp).size(20.dp))
        Text(text=title,
            color=Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}