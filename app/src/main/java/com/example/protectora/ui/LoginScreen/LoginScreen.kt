package com.example.protectora.ui.LoginScreen

import android.annotation.SuppressLint
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.protectora.ui.Navigation.AppScreens
import com.example.protectora.ui.auth.components.EmailOutlinedTextField
import com.example.protectora.ui.auth.components.PasswordOutlinedTextField
import com.example.protectora.ui.auth.starup.AuthState
import com.example.protectora.ui.auth.starup.AuthViewModel
import com.example.protectora.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon

/**
 * Pantalla de inicio de sesión.
 * @param viewModel Modelo de vista para la autenticación.
 * @param navController Controlador de navegación para navegar a otras pantallas.
 *
 */
@Composable
fun LoginScreen(viewModel: AuthViewModel, navController: NavController) {
    // Estado de la UI
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isResponsible by remember { mutableStateOf(false) }
    var responsibleCode by remember { mutableStateOf("") }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val customColor = Color(0xFF005A44)
    val lightGreen = Color(0xFFB2D8CC)

    // Manejar navegación o errores según estado
    LaunchedEffect(uiState) {
        when (uiState) {
            // Manejar diferentes estados de autenticación
            is AuthState.Error -> {
                // Mostrar mensaje de error
                val errorState = uiState as? AuthState.Error
                val message = errorState?.message ?: "Ocurrió un error"
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            AuthState.ErrorCodigoResponsableInvalido -> {
                // Mostrar mensaje de error si el código de responsable es inválido
                Toast.makeText(context, "Código de responsable inválido", Toast.LENGTH_SHORT).show()
            }

            AuthState.SuccessResponsable,

            AuthState.SuccessUsuario -> {
                // Navegar a la pantalla principal si el inicio de sesión es exitoso
                navController.navigate(AppScreens.MainScreen.route) {
                    popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }
// Diseño de la pantalla de inicio de sesión
    Box(modifier = Modifier.fillMaxSize()) {
// Fondo de pantalla
        Image(
            painter = painterResource(id = R.drawable.fondo6),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Botón de volver
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = Color.Black
            )
        }

// Contenido del formulario
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Imagen del logo
            Image(
                painter = painterResource(id = R.drawable.logo3),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))
// Campos de entrada
            Text("Email", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            EmailOutlinedTextField(email = email, onEmailChange = { email = it }, customBorderColor = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Contraseña", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            PasswordOutlinedTextField(password = password, onPasswordChange = { password = it }, customBorderColor = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("¿Eres responsable?", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isResponsible,
                    onCheckedChange = { isResponsible = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = lightGreen,
                        checkedTrackColor = customColor
                    )
                )
            }

            if (isResponsible) {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = responsibleCode,
                    onValueChange = { responsibleCode = it },
                    label = { Text("Código de responsable") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
// Botón de inicio de sesión
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Campos vacíos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (isResponsible && responsibleCode.isBlank()) {
                        Toast.makeText(context, "Ingresa el código de responsable", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    viewModel.login(email, password, if (isResponsible) responsibleCode else null)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = customColor, contentColor = Color.White)
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = NavController(LocalContext.current)
    LoginScreen(viewModel = AuthViewModel(), navController = navController)
}

