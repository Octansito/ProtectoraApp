package com.example.protectora.ui.auth.starup


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protectora.data.repository.Repository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.ConnectException

/**
 * ViewModel encargado de gestionar la lógica de autenticación.
 * Proporciona un flujo de estados de UI (`uiState`) para representar los diferentes estados de la autenticación.
 */
class AuthViewModel : ViewModel() {

    // Al inicio el no hay estado
    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)

    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()
    // Indicador para evitar múltiples inicios de sesión al recomponer pantallas
    var iniciadaSesion = false


    /**
     * Validación de email y contraseña.
     */
    fun esCorrectoEmail(email: String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun validarYLogin(email: String, password: String) {
        when {
            email.isEmpty() || password.isEmpty() -> {
                camposVacios()
            }
            !esCorrectoEmail(email) -> {
                emailIncorrecto()
            }
            password.length < 6 -> {
                contrasenyaMenor()
            }
            else -> {
                login(email, password)
            }
        }
    }
    /**
     * Inicia el proceso de inicio de sesión con el email y contraseña proporcionados.
     * Actualiza el estado de la UI según el resultado de la operación.
     */
    fun login(email: String, password: String) {
        _uiState.value = AuthState.Loading // Cambia el estado a "Cargando"

        viewModelScope.launch {
            val result = Repository.login(email, password) // Realiza la solicitud de login
            _uiState.value = if (result.isSuccess) {
                AuthState.Success // Login exitoso
            } else {
                estadoError(result) // Manejo de errores específicos
            }
        }
    }

    /**
     * Registra un nuevo usuario con el email y contraseña proporcionados.
     * Actualiza el estado de la UI según el resultado de la operación.
     */
    fun register(email: String, password: String, displayName: String="") {
        _uiState.value = AuthState.Loading // Cambia el estado a "Cargando"

        viewModelScope.launch {
            val result = Repository.register(email, password,displayName) // Realiza la solicitud de registro

            _uiState.value =if (result.isSuccess) {
                AuthState.Success // Registro exitoso
            } else {
                estadoError(result) // Manejo de errores específicos
            }
        }
    }
    /**
     * Restablece la contraseña para el usuario con el email proporcionado.
     * Actualiza el estado de la UI según el resultado de la operación.
     */
    fun resetPassword(email: String) {
        _uiState.value = AuthState.Loading // Cambia el estado a "Cargando"
        viewModelScope.launch {
            val result = Repository.resetPassword(email) // Realiza la solicitud de registro
            _uiState.value = if (result.isSuccess) {
                AuthState.Success // Registro exitoso
            } else {
                estadoError(result) // Manejo de errores específicos
            }
        }
    }
    /**
     * Maneja los diferentes tipos de errores que pueden ocurrir durante las operaciones de autenticación.
     * Regresa el estado de error correspondiente basado en la excepción.
     */
    private fun estadoError(result: Result<Unit>) = when (result.exceptionOrNull() as? Exception) {
        is FirebaseAuthInvalidUserException -> {
            Log.e("AuthViewModel", "Error: usuario no encontrado")
            AuthState.Error("Usuario no encontrado")
        }
        is FirebaseAuthInvalidCredentialsException -> {
            Log.e("AuthViewModel", "Error de contraseña no válida")
            AuthState.Error("Contraseña o usuario no válido")
        }
        is ConnectException -> {
            Log.e("AuthViewModel", "Sin conexión a internet")
            AuthState.Error("Sin conexión a internet")
        }
        is FirebaseAuthUserCollisionException -> {
            Log.e("AuthViewModel", "Error: usuario ya registrado")
            AuthState.Error("Usuario ya existe")
        }

        is IllegalArgumentException -> {
            Log.e("AuthViewModel", "Error: ${result.exceptionOrNull()}")
            AuthState.Error("Error: email incorrecto")
        }
        else -> {
            Log.e("AuthViewModel", "Error desconocido: ${result.exceptionOrNull()}")
            AuthState.Error("Error desconocido")
        }
    }

    /**
     * Actualiza el estado de la UI indicando que las contraseñas no coinciden.
     */
    fun contrasenyaNoConciden() {
        _uiState.value = AuthState.ErrorContrasenyaNoConciden
    }
    fun camposVacios() {
        _uiState.value = AuthState.ErrorCamposVacios
    }
    fun emailIncorrecto() {
        _uiState.value = AuthState.ErrorEmailIncorrecto
    }
    fun contrasenyaMenor() {
        _uiState.value = AuthState.ErrorEmailMenor
    }
    /**
     * Realiza el cierre de sesión del usuario actual.
     */
    fun logout() {
        Repository.logout()
    }
    fun estaLogueado(): Boolean {
        return Repository.estaLogueado()
    }
}