package com.example.protectora.ui.auth.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.protectora.R


/**
 * Composable que muestra el logo de la aplicación como una imagen.
 * Permite personalizar su apariencia mediante el uso de un modificador.
 *
 * @param modifier Modificador que se aplica al diseño de la imagen. Por defecto, no tiene modificaciones.
 */
@Composable
fun Logo(modifier: Modifier = Modifier) {
    // Muestra una imagen utilizando un recurso drawable como fuente
    Image(
        painter = painterResource(id = R.drawable.logo3), // Recurso de imagen (logo)
        contentDescription = "Logo", // Descripción de la imagen para accesibilidad
        modifier = modifier // Modificador proporcionado como parámetro
    )
}