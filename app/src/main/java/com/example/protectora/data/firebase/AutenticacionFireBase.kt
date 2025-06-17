package com.example.protectora.data.firebase

import android.annotation.SuppressLint
import com.example.protectora.data.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Clase de utilidad para manejar la autenticación en Firebase.
 */
object AutenticacionFireBase {

    // Instancias de Firebase Authentication y Firestore
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    @SuppressLint("StaticFieldLeak")
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Función para verificar si el email es válido
    fun esCorrectoEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    // Función para registrar un usuario con email y contraseña
    suspend fun register(email: String, password: String, displayName: String = ""): Result<String> {
        return try {
            if (!esCorrectoEmail(email))
                throw IllegalArgumentException("Formato de email inválido")

            firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            firebaseAuth.currentUser!!.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
            ).await()

            val esResponsable = esResponsable(email)
            val rol = if (esResponsable) "responsable" else "usuario"

            val userId = firebaseAuth.currentUser!!.uid

            val userData = hashMapOf(
                "email" to email,
                "displayName" to displayName,
                "rol" to rol
            )

            firestore.collection("users").document(userId).set(userData).await()

            Result.success(rol)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Función para verificar si el usuario es un responsable
    private suspend fun esResponsable(email: String): Boolean {
        val snapshot = firestore.collection("responsables")
            .whereEqualTo("email", email)
            .get()
            .await()

        return !snapshot.isEmpty
    }
    // Función para iniciar sesión con email y contraseña
    suspend fun loginAndGetRole(email: String, password: String): Result<String> {
        return try {
            if (!esCorrectoEmail(email))
                throw IllegalArgumentException("Formato de email inválido")

            // Autenticación normal
            firebaseAuth.signInWithEmailAndPassword(email, password).await()

            // Obtener UID del usuario autenticado
            val userId = firebaseAuth.currentUser!!.uid

            // Leer Firestore para obtener el rol
            val userSnapshot = firestore.collection("users").document(userId).get().await()

            val rol = userSnapshot.getString("rol") ?: "usuario" // Si por algún motivo no encuentra, por defecto usuario

            Result.success(rol)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para restablecer la contraseña
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            if (!esCorrectoEmail(email))
                throw IllegalArgumentException("Formato de email inválido")
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Función para obtener el usuario actual
    fun getCurrentUser(): Usuario {
        val usr = firebaseAuth.currentUser
        return if (usr != null)
            Usuario(usr.displayName ?: "", usr.email ?: "")
        else
            Usuario("", "")
    }
    // Función para cerrar sesión
    fun logout() {
        firebaseAuth.signOut()
    }
    // Función para verificar si el usuario está logueado
    fun estaLogueado(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
