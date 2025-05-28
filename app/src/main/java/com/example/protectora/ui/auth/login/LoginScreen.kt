package com.example.protectora.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.protectora.R
import com.example.protectora.ui.auth.components.EmailOutlinedTextField
import com.example.protectora.ui.auth.components.Encabezado
import com.example.protectora.ui.auth.components.MuestraEstado
import com.example.protectora.ui.auth.components.PasswordOutlinedTextField
import com.example.protectora.ui.auth.starup.AuthState
import com.example.protectora.ui.auth.starup.AuthViewModel
import com.example.protectora.ui.components.GoogleSignInButton
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.protectora.ui.Navigation.AppScreens


/**
 * Composable que representa la pantalla de inicio de sesión.
 * Gestiona el estado de la autenticación y proporciona una interfaz interactiva para ingresar email y contraseña.
 *
 * @param onBack Callback que se ejecuta cuando el usuario presiona el botón "Atrás".
 * @param onLoginSuccess Callback que se ejecuta cuando el inicio de sesión es exitoso.
 * @param viewModel Instancia de `AuthViewModel` para gestionar la lógica de autenticación.
 */
@Composable
fun LoginScreen(
    navController: NavController,
    onBack: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: AuthViewModel = viewModel(),
    onResetPassword: () -> Unit = {}
) {
    // Estado actual de la autenticación observado desde el ViewModel
    val uiState by viewModel.uiState.collectAsState()
    //Creamos las variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val customBorderColor = Color(0xFF0E6655)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        //Fondo de pantalla
        Image(
            painter = painterResource(id= R.drawable.fondo5),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Encabezado con logo, flecha atrásy titulo
            Encabezado(
                onBack = onBack,
                titulo = "Iniciar Sesión",
                modifier = Modifier
                    .weight(1f) // Ocupa un peso proporcional dentro del contenedor
                    .fillMaxWidth() // Ocupa todo el ancho disponible
            )
            Spacer(modifier = Modifier.height(15.dp))

            EmailOutlinedTextField(
                    email = email,
                    onEmailChange = { email = it },
                    customBorderColor = Color(0xFF4CAF50),
                    textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))


            PasswordOutlinedTextField(
                    password = password,
                    onPasswordChange = { password = it },
                    customBorderColor = Color(0xFF4CAF50) ,
                    textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                    onClick = {
                      //viewModel.validarYLogin(email,password)
                        throw Exception("Error en el botón")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = customBorderColor // Verde (puedes cambiar el código HEX si quieres otro tono)
                    )


            ) {
                    Text("Acceder")
            }
            Button(
                onClick = {
                    // 👉 Esto manda a la pantalla de registro
                    navController.navigate(AppScreens.RegisterScreen.route)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = customBorderColor // Verde (puedes cambiar el código HEX si quieres otro tono)
                )

            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(15.dp))
            // Vamos a recuperar la contraseña
            Button(onClick = {
                onResetPassword()
            }) {
                Text("Restablecer contraseña") // Texto dentro del botón
            }
            Spacer(modifier = Modifier.height(15.dp))

            GoogleSignInButton {
            }

            // Manejo del estado de éxito en la autenticación
            if (uiState is AuthState.Success) {
                if (!viewModel.iniciadaSesion) {
                    onLoginSuccess() // Esto navega a la pantalla principal
                    viewModel.iniciadaSesion = true
                }
            } else {
                MuestraEstado(uiState)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable

fun MainScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}