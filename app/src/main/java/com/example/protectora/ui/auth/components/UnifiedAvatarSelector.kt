package com.example.protectora.ui.auth.components

import com.example.protectora.ui.auth.register.AvatarOption


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.protectora.ui.auth.register.RegisterViewModel
import com.example.protectora.R
import androidx.compose.foundation.layout.FlowRow

/**
 * Sealed class que representa las opciones de avatar disponibles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnifiedAvatarSelector(registerViewModel: RegisterViewModel) {

    val avatarList = listOf(
        // Imágenes
        AvatarOption.Image(R.drawable.iconoperro1),
        AvatarOption.Image(R.drawable.iconoperro2),
        AvatarOption.Image(R.drawable.iconoperrogato),
        AvatarOption.Image(R.drawable.iconogato),

        // Animaciones (Lottie JSONs)
        AvatarOption.Animation(R.raw.gifgato1),
        AvatarOption.Animation(R.raw.gifgato2),
        AvatarOption.Animation(R.raw.gifgato3),
        AvatarOption.Animation(R.raw.gifgato4),
        AvatarOption.Animation(R.raw.gifgato5),
        AvatarOption.Animation(R.raw.gifperro1),
        AvatarOption.Animation(R.raw.gifperro2),
        AvatarOption.Animation(R.raw.gifperro3),
        AvatarOption.Animation(R.raw.gifperro4),
        AvatarOption.Animation(R.raw.gifperro5)
    )


    var showSheet by remember { mutableStateOf(false) }


    val avatar = registerViewModel.selectedAvatar.value ?: avatarList.first()
    when (avatar) {
        //Para una imagen
        is AvatarOption.Image -> StaticAvatar(avatar.drawableRes, size = 160.dp) { showSheet = true }
        //Para un gif
        is AvatarOption.Animation -> LottieAvatar(avatar.lottieRes, size = 160.dp) { showSheet = true }
    }


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false }
        ) {
            FlowRow(
                modifier = Modifier.padding(16.dp)
            ) {
                avatarList.forEach { avatarOption ->
                    when (avatarOption) {
                        is AvatarOption.Image -> StaticAvatar(avatarOption.drawableRes, size = 80.dp) {
                            registerViewModel.setSelectedAvatar(avatarOption)
                            showSheet = false
                        }
                        is AvatarOption.Animation -> LottieAvatar(avatarOption.lottieRes, size = 80.dp) {
                            registerViewModel.setSelectedAvatar(avatarOption)
                            showSheet = false
                        }
                    }
                }

            }
        }
    }
}
