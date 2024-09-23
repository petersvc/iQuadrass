package com.example.iquadras.ui.telas.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*

import com.example.iquadras.model.user.User

@Composable
fun UserEditProfileDialog(
    user: User,
    onDismiss: () -> Unit,
    onSaveClick: (User) -> Unit
) {
    var updatedName by remember { mutableStateOf(user.name) }
    var updatedEmail by remember { mutableStateOf(user.email) }
    var updatedPhone by remember { mutableStateOf(user.phone) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text("Editar Perfil do Usuário")
        },
        text = {
            Column {
                TextField(
                    value = updatedName,
                    onValueChange = { updatedName = it },
                    label = { Text("Nome") }
                )
                TextField(
                    value = updatedEmail,
                    onValueChange = { updatedEmail = it },
                    label = { Text("Email") }
                )
                TextField(
                    value = updatedPhone,
                    onValueChange = { updatedPhone = it },
                    label = { Text("Telefone") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedUser = user.copy(
                        name = updatedName,
                        email = updatedEmail,
                        phone = updatedPhone
                    )
                    onSaveClick(updatedUser) // Chama a função de salvar
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}
