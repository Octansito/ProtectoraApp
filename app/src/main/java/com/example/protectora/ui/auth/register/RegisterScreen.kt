package com.example.protectora.ui.auth.register

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.protectora.R
import com.example.protectora.ui.Navigation.AppScreens
import com.example.protectora.ui.auth.starup.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.protectora.ui.auth.components.CustomButton
import com.example.protectora.ui.auth.components.EmailOutlinedTextField
import com.example.protectora.ui.auth.components.NameOutlinedTextField
import com.example.protectora.ui.auth.components.PasswordOutlinedTextField
import com.example.protectora.ui.auth.components.RepeatPasswordOutlinedTextField
import com.example.protectora.ui.auth.components.UnifiedAvatarSelector

@Composable
fun RegisterScreen(viewModel: AuthViewModel, navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val codigoResponsableAsignado = remember { mutableStateOf<String?>(null) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }



    val registerViewModel: RegisterViewModel = viewModel()


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo de pantalla
        Image(
            painter = painterResource(id = R.drawable.fondo7),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 32.dp)  // <-- Aquí controlamos la separación superior
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.Black
            )
        }

        // Formulario centrado
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            UnifiedAvatarSelector(registerViewModel = registerViewModel)

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(16.dp))
            EmailOutlinedTextField(
                email = email,
                onEmailChange = { email = it },
                customBorderColor = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            PasswordOutlinedTextField(
                password = password,
                onPasswordChange = { password = it },
                customBorderColor = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            RepeatPasswordOutlinedTextField(
                repeatPassword = repeatPassword,
                onRepeatPasswordChange = { repeatPassword = it },
                customBorderColor = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            NameOutlinedTextField(
                name = displayName,
                onNameChange = { displayName = it },
                customBorderColor = Color.Black
            )
            Spacer(modifier = Modifier.height(32.dp))

            CustomButton(
                title = "Registrar",
                onClick = {
                    if (email.isBlank() || password.isBlank() || repeatPassword.isBlank() || displayName.isBlank()) {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    } else if (password != repeatPassword) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    } else {
                        val selectedAvatar = registerViewModel.selectedAvatar.value
                        viewModel.registrarUsuario(
                            context = context,
                            email = email,
                            password = password,
                            displayName = displayName,
                            navController = navController,
                            auth = auth,
                            db = db,
                            codigoResponsableAsignado = codigoResponsableAsignado,
                            //Pasamos el avatar seleccionado
                            avatarOption = selectedAvatar
                        )
                    }
                }
            )
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
