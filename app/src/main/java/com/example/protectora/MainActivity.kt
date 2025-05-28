package com.example.protectora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.protectora.ui.Navigation.AppNavigation
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        setContent {
            val navController = rememberNavController()
            val currentUser= auth.currentUser

            Surface(
                modifier=Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background) {

                AppNavigation(navController , currentUser!=null)
            }
        }
    }

}
