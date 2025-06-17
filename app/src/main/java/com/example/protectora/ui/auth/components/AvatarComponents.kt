package com.example.protectora.ui.auth.components



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.airbnb.lottie.compose.*


@Composable
fun StaticAvatar(drawableRes: Int,  size: Dp = 200.dp,onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White)
            .border(2.dp, Color.Black, CircleShape)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            // Ajusta el ContentScale
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun LottieAvatar(
    lottieRes: Int,
    size: Dp = 200.dp,
    onClick: () -> Unit
) {
    // Obtén la composición de la animación
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    // Crea el estado de la animación
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    // Muestra la animación
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White)
            .border(2.dp, Color.Black, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        composition?.let {
            LottieAnimation(
                composition = it,
                progress = { progress },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp) // margen para que no se corte
            )
        }
    }
}
