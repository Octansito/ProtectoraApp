package com.example.protectora.ui.RegisterScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

/**
 * ViewModel que gestiona el avatar seleccionado por el usuario
 */
// Clase AvatarOption para representar las opciones de avatar
sealed class AvatarOption {
    data class Image(val drawableRes: Int): AvatarOption()
    data class Animation(val lottieRes: Int): AvatarOption()
}

class RegisterViewModel : ViewModel() {
    // Estado para el avatar seleccionado
    private val _selectedAvatar = mutableStateOf<AvatarOption?>(null)
    // Propiedad de solo lectura para el avatar seleccionado
    val selectedAvatar: State<AvatarOption?> = _selectedAvatar
// Método para establecer el avatar seleccionado
    fun setSelectedAvatar(avatar: AvatarOption) {
        _selectedAvatar.value = avatar
    }
}
