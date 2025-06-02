package com.example.protectora.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.protectora.ui.SplashScreen.SplashScreen
import com.example.protectora.ui.InitialScreen.InitialScreen
import com.example.protectora.ui.PrincipalScreen.MainScreen
import com.example.protectora.ui.auth.login.LoginScreen
import com.example.protectora.ui.auth.register.RegisterScreen
import com.example.protectora.ui.auth.starup.AuthViewModel


/**
 * Composable que gestiona la navegación entre pantallas de la aplicación.
 */

@Composable
fun AppNavigation(navController: NavHostController, isUserLoggedIn: Boolean){
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route,
    ) {
        // Composable para la pantalla de carga
        composable(AppScreens.SplashScreen.route){
            //Elemento composable que la representa
            SplashScreen(navController=navController)
        }
        // Composable para la pantalla inicial
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
        // Composable para la pantalla principal
        composable(AppScreens.MainScreen.route) {
            MainScreen(navController=navController)
        }
        // Composable para la pantalla de registro
        composable(AppScreens.RegisterScreen.route) {
            val authViewModel: AuthViewModel = viewModel()
            RegisterScreen(viewModel = authViewModel, navController = navController)
        }
        // Composable para la pantalla de inicio de sesión
        composable(AppScreens.LoginScreen.route) {
            //LoginScreen(auth = FirebaseAuth.getInstance(), navController = navController)
            val authViewModel: AuthViewModel = viewModel()
            LoginScreen(viewModel = authViewModel, navController = navController)
        }

    }
}