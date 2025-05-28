package com.example.protectora.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.protectora.ui.PrincipalScreen.PrincipalScreen
import com.example.protectora.ui.SplashScreen.SplashScreen
import com.example.protectora.ui.InitialScreen.InitialScreen
import com.example.protectora.ui.auth.register.RegisterScreen


@Composable
fun AppNavigation(navController: NavHostController, isUserLoggedIn: Boolean){
    //Creamos constante para crear NavController por defecto
    val navController= rememberNavController()
    //Redirige automáticamente si está logueado
    LaunchedEffect(Unit) {
        if (isUserLoggedIn) {
            navController.navigate(AppScreens.PrincipalScreen.route) {
                popUpTo(AppScreens.SplashScreen.route) { inclusive = true }
            }
        }
    }


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
            PrincipalScreen()
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