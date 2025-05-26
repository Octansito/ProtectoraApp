package com.example.protectora.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


@Composable
fun EmailOutlinedTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    customBorderColor: Color,
    textStyle: TextStyle = TextStyle.Default
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email") },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = customBorderColor,
            unfocusedBorderColor = customBorderColor,
            focusedLabelColor = customBorderColor,
            unfocusedLabelColor = customBorderColor,
            cursorColor = customBorderColor
        )
    )
}