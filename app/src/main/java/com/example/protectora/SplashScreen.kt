package com.example.protectora


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
import com.example.protectora.Navigation.AppScreens
import kotlinx.coroutines.delay

/**
 * Pantalla de carga de la aplicación+
 */
//Contempla la lógica
@Composable

fun SplashScreen(navController: NavController){
    //Función en segundo plano
    LaunchedEffect(key1 = true) {
        //Se navegará a la pantalla principal cuando transcurran 5 srgundos
       delay(5000)
        //Evitamos que podamos retroceder a la pantalla de Splash
        navController.popBackStack()
        //Mapeamos la pantalla MainScreen para dirigirnos a ella
        navController.navigate(AppScreens.LoginScreen.route)
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
            painter = painterResource(id=R.drawable.fondo3),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(painter = painterResource(id=R.drawable.logo3),
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