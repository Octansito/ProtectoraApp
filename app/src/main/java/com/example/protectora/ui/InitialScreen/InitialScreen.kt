package com.example.protectora.ui.InitialScreen



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.example.protectora.ui.auth.components.CustomButton
import com.example.protectora.ui.auth.components.TouchableLoginText
import com.example.protectora.R
import com.example.protectora.ui.auth.components.CustomSocialButton

/**
 * Composable que representa la pantalla de inicio de sesión.
 * Gestiona el estado de la autenticación y proporciona una interfaz interactiva para ingresar email y contraseña.
 *
 */
@Composable
fun InitialScreen(  onNavigateToLogin: () -> Unit,
                    onNavigateToRegister: () -> Unit,
) {
    val customColor = Color(0xFF005A44)

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo5),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 48.dp), // Márgenes para evitar que se pegue a los bordes
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Espacio superior flexible


            Image(
                painter = painterResource(id = R.drawable.logo3),
                contentDescription = "Logo de la app",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(24.dp)) // Bordes redondeados
            )

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Tu mascota.",
                color=Color.Black,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Nuestro compromiso",
                color=Color.Black,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onNavigateToRegister()
                },
                modifier=Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
                colors=ButtonDefaults.buttonColors(
                    containerColor = customColor,
                    contentColor = Color.White
                ),
                border = BorderStroke(3.dp, Color.Black), // Borde blanco de 2 dp
                ){
                Text("Sign up",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.height(8.dp))

            CustomSocialButton(
                painter = painterResource(id = R.drawable.google),
                title = "Continuar con Google",
                onClick = {
                    // Lógica de Google login
                }
            )

            Spacer(modifier = Modifier.height(8.dp))


            CustomSocialButton(
                painter = painterResource(id = R.drawable.facebook_logo),
                title = "Continuar con Facebook",
                onClick = {
                    // Lógica de Facebook login
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            TouchableLoginText(onNavigateToLogin = onNavigateToLogin)

            Spacer(modifier = Modifier.weight(1f))
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InitialScreenPreview() {
    InitialScreen(
        onNavigateToLogin = {},
        onNavigateToRegister = {},
    )
}