package com.example.protectora.ui.auth.components

import com.example.protectora.ui.auth.register.AvatarOption


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
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

    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .border(3.dp, Color.Black, CircleShape)
            .clickable { showSheet = true }
    ) {
        val avatar = registerViewModel.selectedAvatar.value ?: avatarList.first()

        when (avatar) {
            is AvatarOption.Image -> StaticAvatar(avatar.drawableRes) {}
            is AvatarOption.Animation -> LottieAvatar(avatar.lottieRes) {}
        }
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
                        is AvatarOption.Image -> StaticAvatar(avatarOption.drawableRes) {
                            registerViewModel.setSelectedAvatar(avatarOption)
                            showSheet = false
                        }
                        is AvatarOption.Animation -> LottieAvatar(avatarOption.lottieRes) {
                            registerViewModel.setSelectedAvatar(avatarOption)
                            showSheet = false
                        }
                    }
                }

            }
        }
    }
}
