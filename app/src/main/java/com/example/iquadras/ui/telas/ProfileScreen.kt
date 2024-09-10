package com.example.iquadras.ui.telas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.model.user.User

@Composable
fun ProfileScreen(
    user: User, // Usando a classe User existente
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Perfil do Usuário",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(text = "Nome: ${user.name}")
        Text(text = "Email: ${user.email}")
        Text(text = "Telefone: ${user.phoneNumber}")

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = onEditClick) {
                Text("Editar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onDeleteClick) {
                Text("Deletar")
            }
        }
    }
}
