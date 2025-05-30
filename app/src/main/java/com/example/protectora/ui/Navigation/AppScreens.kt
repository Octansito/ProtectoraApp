package com.example.protectora.ui.Navigation

sealed class AppScreens(val route:String){
    object SplashScreen: AppScreens("splash_screen")
    object InitialScreen: AppScreens("initial_screen")
    object RegisterScreen: AppScreens("register_screen")
    object LoginScreen: AppScreens("login_screen")
   object MainScreen: AppScreens("main_screen")

}