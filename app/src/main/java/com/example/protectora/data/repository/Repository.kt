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
     * Inicia sesión con un correo electrónico y contraseña utilizando la capa de autenticación.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Un resultado de la operación de inicio de sesión.
     */
    suspend fun login(email: String, password: String) =
        AutenticacionFireBase.login(email, password)

   /**
    * Registra un nuevo usuario con un correo electrónico, contraseña
    */
   suspend fun register(
       email: String,
       password: String,
       displayName: String,
       rol: String
   ): Result<Unit> {
       return try {
           val auth = FirebaseAuth.getInstance()
           val firestore = FirebaseFirestore.getInstance()

           val authResult = auth.createUserWithEmailAndPassword(email, password).await()
           val uid = authResult.user?.uid ?: throw Exception("UID no encontrado")

           val userData = mutableMapOf<String, Any>(
               "email" to email,
               "displayName" to displayName,
               "rol" to rol
           )

           // Si es responsable, generar código único
           if (rol == "responsable") {
               val codigo = generateUniqueResponsableCode()
               userData["codigoResponsa"] = codigo
           }

           firestore.collection("users").document(uid).set(userData).await()

           Result.success(Unit)
       } catch (e: Exception) {
           Result.failure(e)
       }
   }
    suspend fun generateUniqueResponsableCode(): String {
        val firestore = FirebaseFirestore.getInstance()
        var code: String
        var exists: Boolean

        do {
            // Puedes usar cualquier lógica de código, esto es solo un ejemplo
            code = "RRF" + (100000..999999).random()
            val snapshot = firestore.collection("users")
                .whereEqualTo("codigoResponsa", code)
                .get()
                .await()
            exists = !snapshot.isEmpty
        } while (exists)

        return code
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