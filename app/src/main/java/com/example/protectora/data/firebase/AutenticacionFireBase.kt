package com.example.protectora.data.firebase

import com.example.protectora.data.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

/**
 * Clase encargada de manejar la autenticación con Firebase.
 * Proporciona métodos para iniciar sesión, registrarse, restablecer contraseñas, y gestionar el usuario actual.
 */
object AutenticacionFireBase {

    //Instancia de FirebaseAuth para interactuar con los servicios de Firebase Authentication
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Verifica si el formato del correo electrónico es válido.
     */
    fun esCorrectoEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Registro de nuevo usuario con email y contraseña y nombre para mostrar
     * @param email El correo electrónico del usuario.
     *      * @param password La contraseña del usuario.
     *      * @param displayName El nombre para mostrar del usuario (opcional).
     *      * @return Un [Result] que indica éxito o fracaso de la operación.
     */
    suspend fun register(email: String, password: String, displayName: String = ""): Result<Unit> {
        return try {
            if(!esCorrectoEmail(email))
                throw IllegalArgumentException("Formato de email inválido")
            // Crea un nuevo usuario en Firebase.
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            // Actualiza el perfil del usuario con el nombre para mostrar.
            firebaseAuth.currentUser!!.updateProfile(
                UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(displayName)
                    .build()
            )?.await()

            Result.success(Unit) // Registro exitoso
        } catch (e: Exception) {
            Result.failure(e) // Manejo de error
        }
    }


    /**
     ** Inicia sesión con un correo electrónico y contraseña.
     *      * @param email El correo electrónico del usuario.
     *      * @param password La contraseña del usuario.
     *      * @return Un [Result] que indica éxito o fracaso de la operación.
     */
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            if(!esCorrectoEmail(email))
                throw IllegalArgumentException("Formato de email inválido")
            firebaseAuth
                .signInWithEmailAndPassword(email, password).await() // Llama a Firebase para autenticar al usuario.
            Result.success(Unit) // Login exitoso
        } catch (e: Exception) {
            Result.failure(e) // Manejo de error
        }
    }
    /**
     * Envía un correo para restablecer la contraseña de un usuario.
     * @param email El correo electrónico del usuario.
     * @return Un [Result] que indica éxito o fracaso de la operación.
     */
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            if(!esCorrectoEmail(email))
                throw IllegalArgumentException("Formato de email inválido")
            // Envía el correo de restablecimiento de contraseña.
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit) // Email enviado con éxito
        } catch (e: Exception) {
            Result.failure(e) // Manejo de error
        }
    }

    /**
     * Obtiene el usuario actualmente autenticado en Firebase.
     * @return El usuario actual o """" si no hay un usuario autenticado.
     */
    // fun getCurrentUser() = firebaseAuth.currentUser
    fun getCurrentUser(): Usuario {
        val usr=firebaseAuth.currentUser
        return if(usr!=null)
            Usuario(usr.displayName?:"",usr.email?:"")
        else
            Usuario("","")

    }

    /**
     * Cierra la sesión del usuario actualmente autenticado.
     */
    fun logout() {
        firebaseAuth.signOut() // Cierra la sesión del usuario.
    }

    /**
     * Verifica si el usuario está actualmente autenticado en Firebase.
     */
    fun estaLogueado(): Boolean {
        return firebaseAuth.currentUser!=null
    }

}