package com.example.protectora.ui.auth.register

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
    private val _selectedAvatar = mutableStateOf<AvatarOption?>(null)
    val selectedAvatar: State<AvatarOption?> = _selectedAvatar

    fun setSelectedAvatar(avatar: AvatarOption) {
        _selectedAvatar.value = avatar
    }
}
