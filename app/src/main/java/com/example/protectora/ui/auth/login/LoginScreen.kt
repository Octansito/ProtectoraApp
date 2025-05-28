package com.example.protectora.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import com.example.protectora.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.protectora.ui.auth.components.EmailOutlinedTextField
import com.example.protectora.ui.auth.components.PasswordOutlinedTextField

import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(auth: FirebaseAuth){

    Box(modifier=Modifier
        .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.fondo6),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.back),
                contentDescription = "Volver atrás"
            )
            Text("Email",
                color=Color.Black,
                fontWeight= FontWeight.Bold,
                fontSize=40.sp
            )
            EmailOutlinedTextField(
                email = "",
                onEmailChange = { },
                customBorderColor = Color.Black
            )
            Text("Contraseña",
                color=Color.Black,
                fontWeight= FontWeight.Bold,
                fontSize=40.sp
            )
            PasswordOutlinedTextField(
                password = "",
                onPasswordChange = { },
                customBorderColor = Color.Black,
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    // Puedes pasar null si no necesitas auth en el preview
    LoginScreen(auth = FirebaseAuth.getInstance())
}
