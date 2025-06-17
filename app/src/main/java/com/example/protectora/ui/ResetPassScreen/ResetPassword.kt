package com.example.protectora.ui.ResetPassScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.protectora.data.firebase.AutenticacionFireBase.esCorrectoEmail
import com.example.protectora.ui.auth.components.MuestraEstado
import com.example.protectora.ui.auth.starup.AuthState
import com.example.protectora.ui.auth.starup.AuthViewModel



/**
 * Composable que representa la pantalla con la opción de restablecer contraseña.
 *
 * @param onBack Callback que se ejecuta cuando el usuario presiona el botón "Atrás"
 * @param viewModel Instancia de `AuthViewModel` para gestionar la lógica de autenticación.
 */
@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit = {}, // Acción por defecto para el botón "Atrás"
    viewModel: AuthViewModel = viewModel() // ViewModel para la autenticación
) {
    // Estado actual de la autenticación observado desde el ViewModel
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("pepe@correo.es") }


    // Estructura de la interfaz
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        //Si está introduciendo el email o hay error
        if(uiState is AuthState.Idle || !(uiState is AuthState.Success)) {
            // Campo de texto para el email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(16.dp))

            // Envía correo para restablecer la contraseña
            Button(onClick = {
                //si no son correctos los campos muestra un error
                if(email.isEmpty())
                    viewModel.camposVacios()
                else if(!esCorrectoEmail(email))
                    viewModel.emailIncorrecto()
                else//enviamo email(hay que mirar en el correo no deseado)
                    viewModel.resetPassword(email)
            }) {
                Text("Restablecer contraseña")

            }
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre elementos
            MuestraEstado(uiState)
        }
        // Si se envía correcto
        if (uiState is AuthState.Success) {
            // onLoginSuccess() // Navega a la siguiente pantalla
            viewModel.iniciadaSesion = true // Marca la sesión como iniciada
            // onBack()
            Text("Se ha enviado un email para restablecer la contraseña")
            Button(onClick = {
                onBack()
            }) {
                Text("Volver a Inicio")
            }
            // }
        }/* else {
            // Muestra el estado actual de la autenticación (cargando, error, etc.)
            MuestraEstado(uiState)
        }*/
        Spacer(modifier = Modifier.height(100.dp)) // Espacio final
    }
}
@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen()
}