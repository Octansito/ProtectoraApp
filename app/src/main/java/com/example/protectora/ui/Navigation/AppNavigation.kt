package com.example.protectora.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.protectora.ui.PrincipalScreen.PrincipalScreen
import com.example.protectora.ui.SplashScreen.SplashScreen
import com.example.protectora.ui.InitialScreen.InitialScreen
import com.example.protectora.ui.auth.register.RegisterScreen


@Composable
fun AppNavigation(){
    //Creamos constante para crear NavController por defecto
    val navController= rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route,
    ) {
        composable(AppScreens.SplashScreen.route){
            //Elemento composable que la representa
            SplashScreen(navController)
        }
        composable(AppScreens.PrincipalScreen.route){
            //Elemento composable que la representa
            PrincipalScreen(navController)
        }
        composable(AppScreens.InitialScreen.route){
            //Elemento composable que la representa
            InitialScreen(
                onNavigateToLogin = {
                    navController.navigate(AppScreens.LoginScreen.route)
                },
                onNavigateToRegister = {
                    navController.navigate(AppScreens.RegisterScreen.route)
                }
            )

        }
        composable(AppScreens.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
    }
}