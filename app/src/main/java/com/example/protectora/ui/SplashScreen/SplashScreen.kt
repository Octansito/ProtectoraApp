package com.example.protectora.ui.SplashScreen


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.protectora.ui.Navigation.AppScreens
import com.example.protectora.ui.auth.starup.AuthViewModel
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.protectora.R


/**
 * Pantalla de carga de la aplicación+
 */
//Contempla la lógica
@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel=viewModel()){

    //Función en segundo plano

    // Esto se ejecuta una vez cuando se compone la pantalla
    LaunchedEffect(Unit) {
        //pausa de 3 segundos
        delay(3000)
        //Llama a la función estaLogueado() del authViewModel para ver si el usuario ya ha iniciado sesión.
        val estaLogueado = authViewModel.estaLogueado()
        Log.d("SplashScreen", "¿Está logueado? $estaLogueado")
        //Si está logueado va a la pantalla principal
        val destino = if (estaLogueado) {
            AppScreens.MainScreen.route
        } else {
            //Sino va a la pantalla inicial
            AppScreens.InitialScreen.route
        }

        // Verifica si ya se ha navegado, y limpia el backstack de SplashScreen
        navController.navigate(destino) {
            popUpTo(AppScreens.SplashScreen.route) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    // Puedes mostrar aquí tu logo, animación o imagen de carga
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Cargando...")
    }
    //Llamamos al método Splash
    Splash()

}

//Función que contiene el diseño
@Composable
fun Splash(){
    Box(
        modifier=Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id= R.drawable.fondo3),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(painter = painterResource(id= R.drawable.logo3),
                contentDescription = "Logo Protectora",
                Modifier.size(150.dp, 150.dp)
                    //Bordes redondeados
                    .clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("Bienvenido a PawHearts",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text("Welcome to PawHearts",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold )
        }
    }

}




@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    Splash()
}