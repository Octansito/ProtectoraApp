package com.example.protectora.Navigation

sealed class AppScreens(val route:String){
    object SplashScreen: AppScreens("splash_screen")
    object LoginScreen: AppScreens("login_screen")

}