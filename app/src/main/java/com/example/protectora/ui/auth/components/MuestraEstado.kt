package com.example.protectora.ui.auth.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.protectora.ui.auth.starup.AuthState


/**
 * Composable que muestra el estado actual de la autenticación.
 * Dependiendo del estado proporcionado, renderiza diferentes elementos de UI.
 *
 * @param uiState El estado de la autenticación, representado como una instancia de la clase sellada `AuthState`.
 */
@Composable
fun MuestraEstado(uiState: AuthState) {
    // Estructura condicional para renderizar diferentes contenidos según el estado de autenticación
    when (uiState) {
        // Estado inicial donde no se realiza ninguna acción
        is AuthState.Idle -> {
            // No se muestra ningún contenido en este estado
        }

        // Estado de carga que muestra un indicador de progreso circular
        is AuthState.Loading -> CircularProgressIndicator()

        // Estado de éxito, muestra un mensaje indicando que el login fue exitoso
        is AuthState.Success -> {
            Text("Login successful!") // Muestra un mensaje de éxito
            // Lógica adicional comentada: podría realizar una acción como iniciar sesión
            // if(!iniciadaSesion) onLoginSuccess()
        }

        // Error específico: las contraseñas no coinciden
        is AuthState.ErrorContrasenyaNoConciden -> {
            Text(
                text = "Las contraseñas no coinciden", // Muestra el error
                color = MaterialTheme.colorScheme.error // Aplica color de error del tema actual
            )
        }
        is AuthState.ErrorCamposVacios -> {
            Text(
                text = "Rellena todos los campos", // Muestra el error
                color = MaterialTheme.colorScheme.error // Aplica color de error del tema actual
            )

        }
        is AuthState.ErrorEmailIncorrecto -> {
            Text(
                text = "Email incorrecto", // Muestra el error
                color = MaterialTheme.colorScheme.error // Aplica color de error del tema actual
            )
        }
        is AuthState.ErrorEmailMenor -> {
            Text(
                text = "La contraseña debe tener al menos 6 caracteres", // Muestra el error
                color = MaterialTheme.colorScheme.error // Aplica color de error del tema actual
            )
        }
        // Estado de error genérico, muestra el mensaje de error proporcionado
        is AuthState.Error -> {
            Text(
                text = uiState.message, // Mensaje de error
                color = MaterialTheme.colorScheme.error // Aplica color de error del tema actual
            )
        }

        AuthState.ErrorCodigoResponsableInvalido -> TODO()
        AuthState.SuccessResponsable -> TODO()
        AuthState.SuccessUsuario -> TODO()
    }
}