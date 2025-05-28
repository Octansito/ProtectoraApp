package com.example.protectora.ui.auth.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun  TouchableLoginText(onNavigateToLogin: () -> Unit) {


    var isPressed by remember { mutableStateOf(false) }

    Text(
        text = "Log In",
        color = Color.Black,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = if (isPressed) TextDecoration.Underline else TextDecoration.None,
        modifier = Modifier
            .padding(24.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onNavigateToLogin()
                    }
                )
            }
    )

}