package com.example.protectora.ui.auth.register

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.protectora.ui.Navigation.AppScreens
import com.example.protectora.ui.auth.starup.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun RegisterScreen(viewModel: AuthViewModel,navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val codigoResponsableAsignado = remember { mutableStateOf<String?>(null) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank() || displayName.isBlank()) {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.registrarUsuario(
                        context = context,
                        email = email,
                        password = password,
                        displayName = displayName,
                        navController = navController,
                        auth = auth,
                        db = db,
                        codigoResponsableAsignado = codigoResponsableAsignado
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }

    // Mostrar AlertDialog si el usuario registrado es responsable
    if (codigoResponsableAsignado.value != null) {
        AlertDialog(
            onDismissRequest = {
                codigoResponsableAsignado.value = null
                navController.navigate(AppScreens.MainScreen.route)
            },
            title = { Text("Responsable registrado") },
            text = {
                Text("Tu código de responsable es: ${codigoResponsableAsignado.value}")
            },
            confirmButton = {
                Button(
                    onClick = {
                        codigoResponsableAsignado.value = null
                        navController.navigate(AppScreens.MainScreen.route)
                    }
                ) {
                    Text("Entendido")
                }
            }
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val navController = NavController(LocalContext.current)
    RegisterScreen(
        viewModel = AuthViewModel(),navController)
}
