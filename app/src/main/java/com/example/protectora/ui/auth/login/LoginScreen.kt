package com.example.protectora.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import com.example.protectora.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.protectora.ui.auth.components.EmailOutlinedTextField
import com.example.protectora.ui.auth.components.PasswordOutlinedTextField
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(auth: FirebaseAuth){
    val customColor= Color(0xFF005A44)
    val lightGreen = Color(0xFFB2D8CC)  // Verde claro para la "bolita"
    var isResponsible by remember { mutableStateOf(false) }
    var responsibleCode by remember { mutableStateOf("") }
    Box(modifier=Modifier
        .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.fondo6),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(id = R.drawable.logo3),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp)) // Bordes redondeados
        )
        Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Volver atrás",
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 16.dp)
                    .size(32.dp),
                tint = Color.Black
            )
            Text("Email",
                color=Color.Black,
                fontWeight= FontWeight.Bold,
                fontSize=40.sp
            )
            EmailOutlinedTextField(
                email = "",
                onEmailChange = { },
                customBorderColor = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("Contraseña",
                color=Color.Black,
                fontWeight= FontWeight.Bold,
                fontSize=40.sp
            )
            PasswordOutlinedTextField(
                password = "",
                onPasswordChange = { },
                customBorderColor = Color.Black,
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿Eres responsable?", color = Color.Black, fontSize = 18.sp, fontWeight= FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isResponsible,
                    onCheckedChange = { isResponsible = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = lightGreen,
                        checkedTrackColor = customColor,
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = Color.LightGray
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
                        unfocusedBorderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black,
                        cursorColor = Color.Black
                    )

                )
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    // Puedes pasar null si no necesitas auth en el preview
    LoginScreen(auth = FirebaseAuth.getInstance())
}
