package com.example.protectora.ui.auth.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.airbnb.lottie.compose.*


@Composable
fun StaticAvatar(drawableRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun LottieAvatar(lottieRes: Int, onClick: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val progress by animateLottieCompositionAsState(composition)

    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Black, CircleShape)
            .clickable { onClick() }
    ) {
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier.fillMaxSize()
        )
    }
}
