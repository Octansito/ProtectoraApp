package com.example.protectora.ui.PrincipalScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.protectora.ui.auth.starup.AuthState
import com.example.protectora.ui.components.ActionItem
import com.example.protectora.ui.components.AppBar


/**
 * representa la pantalla principal de la aplicación.
 * La modificáis según vuestras necesidades
 */
@Composable
fun PrincipalScreen(navController: NavController,
                    onLogout: () -> Unit = {},//accion de logout
                    viewModel: PrincipalViewModel = viewModel()
)
{
    LaunchedEffect(true) {
        Log.d("PrincipalScreen", "Entramos en PrincipalScreen")
    }
    //añadimos una acción a la barra de navegación para salir de la app
    val accionesBarra=listOf(
        //Acción de cerrar sesión
        ActionItem(
            name = "Cerra Sesión",
            icon = Icons.Filled.ExitToApp,
            action = {
                viewModel.cerrarSesion()
                onLogout()
            }
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                tituloPantallaActual = "Principal",
                puedeNavegarAtras = false,
                listaAcciones = accionesBarra
            )
        }
    ){padding->
        Box(modifier = Modifier.padding(padding)) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(text = "Nombre : ${uiState.usuario.nombre} ")
                Text(text = "Email: ${uiState.usuario.email}")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PrincipalScreenPreview() {
    val navController = rememberNavController()
    PrincipalScreen(navController = navController)
}