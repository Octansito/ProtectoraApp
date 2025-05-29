package com.example.protectora.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.protectora.ui.SplashScreen.SplashScreen
import com.example.protectora.ui.InitialScreen.InitialScreen
import com.example.protectora.ui.PrincipalScreen.MainScreen
import com.example.protectora.ui.auth.login.LoginScreen
import com.example.protectora.ui.auth.register.RegisterScreen
import com.google.firebase.auth.FirebaseAuth

/**
 * Composable que gestiona la navegación entre pantallas de la aplicación.
 */

@Composable
fun AppNavigation(navController: NavHostController, isUserLoggedIn: Boolean){

    //Redirige automáticamente si está logueado
    LaunchedEffect(Unit) {
        if (isUserLoggedIn) {
            navController.navigate(AppScreens.MainScreen.route) {
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

        composable(AppScreens.InitialScreen.route) {
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
        composable(AppScreens.MainScreen.route) {
            MainScreen()
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(auth = FirebaseAuth.getInstance(), navController = navController)
        }


//        composable(AppScreens.RegisterScreen.route){
//            RegisterScreen(navController = navController)
//        }
    }
}