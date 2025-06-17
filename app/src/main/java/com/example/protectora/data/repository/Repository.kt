package com.example.protectora.data.repository

import com.example.protectora.data.firebase.AutenticacionFireBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * Objeto Repository que actúa como una capa de abstracción para interactuar con el objeto AutenticacionFireBase.
 * Proporciona métodos suspendidos para las operaciones de autenticación y gestión del usuario actual.
 */
object Repository {
    /**
     * Inicia sesión con un correo electrónico y una contraseña.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un resultado de la operación de inicio de sesión.
     */
    suspend fun loginAndGetRole(email: String, password: String): Result<String> {
        return AutenticacionFireBase.loginAndGetRole(email, password)
    }

    /**
     * Obtiene el usuario actualmente autenticado a través de la capa de autenticación.
     * @return El usuario actual o null si no hay un usuario autenticado.
     */
    fun getCurrentUser() = AutenticacionFireBase.getCurrentUser()

    /**
     * Cierra la sesión del usuario actual utilizando la capa de autenticación.
     */
    fun logout() = AutenticacionFireBase.logout()

    /**
     * Envía un correo para restablecer la contraseña del usuario especificado.
     * @param email El correo electrónico del usuario.
     * @return Un resultado de la operación de restablecimiento de contraseña.
     */
    suspend fun resetPassword(email: String) =
        AutenticacionFireBase.resetPassword(email)

    /**
     * Obtiene el rol del usuario actual.
     */
    suspend fun getUserRole(): String? {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return null

        val snapshot = FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .await()

        return snapshot.getString("rol")
    }
    suspend fun getUserRoleAndCode(): Pair<String?, String?> {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return Pair(null, null)

        val snapshot = FirebaseFirestore.getInstance()
            .collection("users")
            .document(uid)
            .get()
            .await()

        val rol = snapshot.getString("rol")
        val codigo = snapshot.getString("codigoResponsable")

        return Pair(rol, codigo)
    }


}