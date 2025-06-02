package com.example.protectora.ui.auth.starup


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.protectora.data.repository.Repository
import com.example.protectora.ui.Navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
    fun estaLogueado(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }
    init {
        iniciadaSesion = estaLogueado()
    }

    /**
     * Inicia sesión con el email y contraseña proporcionados.
     */
    fun login(email: String, password: String, inputCode: String? = null) {
        _uiState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                // 1. Autenticar con Firebase
                val authResult = FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .await()

                val user = authResult.user
                val uid = user?.uid ?: throw Exception("No se encontró UID del usuario")

                // 2. Obtener datos del usuario en Firestore
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .get()
                    .await()

                val rol = snapshot.getString("rol")
                val codigoResponsable = snapshot.getString("codigoResponsable")

                when (rol) {
                    "responsable" -> {
                        if (inputCode == codigoResponsable) {
                            _uiState.value = AuthState.SuccessResponsable
                        } else {
                            _uiState.value = AuthState.ErrorCodigoResponsableInvalido
                            FirebaseAuth.getInstance().signOut()
                        }
                    }

                    "usuario" -> _uiState.value = AuthState.SuccessUsuario
                    else -> _uiState.value = AuthState.Error("Rol no definido")
                }

            } catch (e: Exception) {
                _uiState.value = AuthState.Error("Error al iniciar sesión: ${e.message}")
            }
        }
    }

    val auth = FirebaseAuth.getInstance()

    /**
     * Registra un nuevo usuario con el email y contraseña proporcionados.
     */
    fun registrarUsuario(
        context: Context,
        email: String,
        password: String,
        displayName: String,
        navController: NavController,
        auth: FirebaseAuth,
        db: FirebaseFirestore,
        codigoResponsableAsignado: MutableState<String?>
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener

                    db.collection("responsables")
                        .whereEqualTo("displayName", displayName)
                        .get()
                        .addOnSuccessListener { documents ->
                            val userData: HashMap<String, Any> = if (!documents.isEmpty) {
                                val doc = documents.documents[0]
                                val codigoResponsable = doc.getString("codigoResponsable") ?: ""
                                codigoResponsableAsignado.value = codigoResponsable

                                hashMapOf(
                                    "email" to email,
                                    "displayName" to displayName,
                                    "rol" to "responsable",
                                    "codigoResponsable" to codigoResponsable
                                )
                            } else {
                                hashMapOf(
                                    "email" to email,
                                    "displayName" to displayName,
                                    "rol" to "usuario"
                                )
                            }

                            db.collection("users").document(uid).set(userData)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()

                                    // Solo navegamos si no es responsable (para mostrar alerta en otro caso)
                                    if (codigoResponsableAsignado.value == null) {
                                        navController.navigate(AppScreens.MainScreen.route)
                                    }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Error guardando datos", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error consultando responsables", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "Error al registrar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
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


}