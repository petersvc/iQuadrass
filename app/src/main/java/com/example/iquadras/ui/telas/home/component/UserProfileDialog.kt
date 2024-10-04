package com.example.iquadras.ui.telas.home.component

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.iquadras.model.user.DTOUser
import com.example.iquadras.model.user.User
import com.example.iquadras.restClient.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserProfileDialog(
    userDTO: User,
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    onLogoffClick: () -> Unit
) {
    var user by remember { mutableStateOf(userDTO) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showEditDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) } // Controla a exibição do diálogo de confirmação
    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var mensagemSucesso by remember { mutableStateOf<String?>(null) }

    //Sempre que o dialog for exibido, buscamos o usuário do banco
    LaunchedEffect(userDTO.id) {
        user = getUser(userDTO.id.toLong())
    }

    // user = userDTO

    // Exibe um texto de carregamento enquanto os dados não foram carregados
//    if (user == null) {
//        Text("Carregando dados do usuário...")
//        return
//    }

    // O diálogo principal de perfil do usuário (somente se `showEditDialog` for falso)
    if (!showEditDialog && !showConfirmDeleteDialog) {
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
                    Text("Nome: ${user?.name}")
                    Text("Email: ${user?.email}")
                    Text("Telefone: ${user?.phone}")
                }
            },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            showEditDialog = true // Fecha o UserProfileDialog e abre o UserEditDialog
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Editar", color = Color.Gray)
                    }
                    Button(
                        onClick = {
                            showConfirmDeleteDialog = true // Exibe o diálogo de confirmação antes de deletar
                        },
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

    // Exibe o diálogo de edição de perfil
    if (showEditDialog) {
        UserEditProfileDialog(
            user = user,  // Passa o estado atual do usuário
            onDismiss = {
                showEditDialog = false // Fecha o diálogo de edição
            },
            onSaveClick = { updatedUser ->
                // Atualiza o estado do usuário com os dados alterados
                scope.launch(Dispatchers.IO) {
                    try {
                        updateUser(updatedUser)  // Faz a atualização no backend
                        withContext(Dispatchers.Main) {
                            user = updatedUser  // Atualiza o estado do usuário localmente
                            showEditDialog = false  // Fecha o diálogo de edição
                        }
                    } catch (e: Exception) {
                        mensagemErro = e.message
                    }
                }
            }
        )
    }

    // Exibe o diálogo de confirmação de exclusão
    if (showConfirmDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDeleteDialog = false }, // Fecha o diálogo de confirmação
            title = {
                Text("Confirmação de Exclusão")
            },
            text = {
                Text("Você tem certeza de que deseja excluir este usuário? Essa ação não pode ser desfeita.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            user?.id?.let { deleteUser(it.toLong()) }
                            withContext(Dispatchers.Main) {
                                mensagemSucesso = "Usuário deletado com sucesso"
                                onDeleteClick()
                                onLogoffClick()
                            }
//                            userDao.delete(user!!) { success ->
//                                if (success) {
//                                    onDeleteClick()
//                                    onLogoffClick()
//                                }
//                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Confirmar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDeleteDialog = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            }
        )
        mensagemErro?.let {
            LaunchedEffect(it) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                mensagemErro = null
            }
        }

        mensagemSucesso?.let {
            LaunchedEffect(it) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                mensagemSucesso = null
            }
        }
    }
}

suspend fun getUser(userId: Long): User {
    return withContext(Dispatchers.IO) {
        val response = RetrofitClient.userService.getUser(userId)
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Usuário não encontrado")
        } else {
            throw Exception("Falha ao buscar usuário: ${response.errorBody()?.string()}")
        }
    }
}

suspend fun updateUser(user: User): Any {
    return withContext(Dispatchers.IO) {
        val response = RetrofitClient.userService.updateUser(user.id.toLong(), user)
        if (response.isSuccessful) {
            response.body() ?: "Usuário atualizado com sucesso" // Lida com resposta nula ou string vazia
        } else {
            throw Exception("Falha ao atualizar usuário: ${response.errorBody()?.string()}")
        }
    }
}

suspend fun deleteUser(userId: Long): Any {
    return withContext(Dispatchers.IO) {
        val response = RetrofitClient.userService.deleteUser(userId)
        if (response.isSuccessful) {
            response.body() ?: "Usuário deletado com sucesso" // Lida com resposta nula ou string vazia
        } else {
            throw Exception("Falha ao deletar usuário: ${response.errorBody()?.string()}")
        }
    }
}