package com.example.protectora.ui.auth.components



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Composable que representa un encabezado genérico con un botón de retroceso,
 * un logo y un título. Este diseño utiliza un contenedor vertical (Column)
 * para organizar los elementos en un diseño apilado.
 *
 * @param onBack Acción que se ejecuta al pulsar el botón de retroceso.
 * @param titulo Título que se muestra en el encabezado.
 * @param modifier Modificador para personalizar el diseño del contenedor principal.
 */
@Composable
fun Encabezado(
    onBack: () -> Unit = {}, // Acción predeterminada: no hacer nada si no se especifica.
    titulo: String = "", // Título predeterminado: vacío.
    modifier: Modifier = Modifier // Modificador predeterminado: sin modificaciones.
) {
    // Contenedor principal de diseño vertical
    Column(modifier = modifier) {

        // Espaciador inicial para crear un margen superior
        Spacer(
            modifier = Modifier.size(40.dp) // Espaciador de 40 dp
        )

        // Icono de flecha hacia atrás con funcionalidad de clic
        Icon(
            imageVector = Icons.Filled.ArrowBack, // Icono de flecha hacia atrás
            contentDescription = "Atrás", // Descripción para accesibilidad
            modifier = Modifier
                .size(48.dp) // Tamaño del icono
                .align(Alignment.Start) // Alineación a la izquierda dentro del contenedor
                .clickable { onBack() }, // Ejecuta la acción `onBack` al hacer clic
        )

        // Espaciador para separar elementos de manera proporcional
        Spacer(modifier = Modifier.weight(1f))

        // Composable para mostrar el logo de la aplicación
       Logo(
            modifier = Modifier
                .size(height = 200.dp, width = 300.dp) // Tamaño del logo
                .align(Alignment.CenterHorizontally) // Centrado horizontalmente
       )

        // Otro espaciador proporcional para mantener el diseño balanceado
        Spacer(modifier = Modifier.weight(1f))

        // Título del encabezado
        Text(
            text = titulo, // Texto proporcionado como parámetro
            style = MaterialTheme.typography.displaySmall // Estilo del texto según el tema de la aplicación
        )

//        // Espaciador final proporcional para ajustar la distancia al borde inferior
//        Spacer(modifier = Modifier.weight(1f))
    }
}