package com.example.protectora.ui.auth.starup

import com.example.protectora.data.model.Usuario

/**
 * Clase sellada que representa los diferentes estados de la autenticación.
 * Es parecido a un Enum pero con más opciones.
 */
sealed class AuthState {
    object Idle : AuthState() // Estado inicial sin actividad
    object Loading : AuthState() // Estado de carga
    object Success : AuthState() // Estado de éxito
    object SuccessResponsable : AuthState()
    object SuccessUsuario : AuthState()
    object ErrorContrasenyaNoConciden : AuthState() // Error: contraseñas no coinciden
    object ErrorCamposVacios : AuthState() // Error: campos vacíos
    object ErrorEmailIncorrecto : AuthState() // Error: email incorrecto
    object ErrorEmailMenor : AuthState() // Error: email incorrecto
    object ErrorCodigoResponsableInvalido : AuthState() // Código responsable no válido

    //Error con mensaje
    data class Error(val message: String) : AuthState()

}