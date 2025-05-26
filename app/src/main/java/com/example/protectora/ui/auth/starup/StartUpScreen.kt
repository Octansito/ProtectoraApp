package com.example.protectora.ui.auth.starup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.protectora.ui.auth.components.Logo


/**
 * Esta pantalla inicial de la App
 * ofrece dos opciones principales al usuario: iniciar sesión o registrarse.
 * Además, incluye un fondo con un degradado vertical, un título, un logotipo y dos botones para la navegación.
 *
 * @param navigateToLogin Acción que se ejecutará al presionar el botón "Iniciar Sesión".
 * @param navigateToRegister Acción que se ejecutará al presionar el botón "Registrarse".
 */

@Composable
fun StarUpScreen(
    navigateToLogin: () -> Unit = {}, // Navegación hacia la pantalla de inicio de sesión
    navigateToRegister: () -> Unit = {}, // Navegación hacia la pantalla de registro
    navigateToPrincipal: () -> Unit = {}, // Navegación hacia la pantalla principal
    viewModel: AuthViewModel = viewModel(),
) {
    //si ya está loggeado vamos  a la pantalla principal
    if (viewModel.estaLogueado()) {
        //evitamos que cargue varias veces la pantalla principal
        if (!viewModel.iniciadaSesion) {
            viewModel.iniciadaSesion = true
            navigateToPrincipal()
        }
    } else {//si no está loggeado cargamos la pantalla de inicio
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.LightGray,
                            Color.Yellow
                        )
                    )
                ), // Fondo con degradado vertical
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "T14 Firebase",
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(modifier = Modifier.weight(1f))
            Logo(
                modifier = Modifier.size(300.dp) // Logotipo
            )
            Spacer(modifier = Modifier.weight(1f))
            Button( // Acción de navegación a iniciar sesión
                onClick = navigateToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp)
            ) {
                Text(text = "Iniciar Sesión")
            }
            Button(// Acción de navegación a registrarse
                onClick = navigateToRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp)
            ) {
                Text(text = "Registrarse")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
