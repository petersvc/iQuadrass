package com.example.iquadras.ui.telas.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UserProfileDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Perfil do Usuário")
                IconButton(onClick = { onDismiss() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Fechar",
                        tint = Color.Gray
                    )
                }
            }
        },
        text = {
            Column {
                Text("Nome: João da Silva555")
                Text("Email: joao.silva@example.com")
                Text("Telefone: (11) 98765-4321")
                Text("ID: 123456")
                // Adicione mais informações fictícias conforme necessário
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { /* Ação de Editar, ainda a ser implementada */ },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Editar", color = Color.Gray)
                }
                Button(
                    onClick = { /* Ação de Deletar, ainda a ser implementada */ },
                    colors = ButtonDefaults.buttonColors(),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Deletar", color = Color.White)
                }
            }
        },
        dismissButton = {
            // O botão de fechar já está no topo como um ícone "X"
        }
    )
}