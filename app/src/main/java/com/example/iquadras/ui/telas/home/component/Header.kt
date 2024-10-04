package com.example.iquadras.ui.telas.home.component

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.iquadras.model.user.User


@Composable
fun Header(
    onLogoffClick: () -> Unit,
    modifier: Modifier = Modifier,
    user: User, // Recebendo o usuário logado
    onReservationsClick: () -> Unit,

    ) {
    var showMainDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) } // Controla se o modal de perfil deve ser exibido

    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = "Main",
//                    tint = Color.Black.copy(alpha = 0.9f),
//                    modifier = Modifier
//                        .size(28.dp)
//                        .padding(top = 0.dp)
//                )
                Text(
                    text = "iQuadras",
                    style = TextStyle(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                        fontSize = 22.sp,
                        color = Color.Black.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.padding(start = 0.dp)
                )
            }
        }
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    painter = painterResource(id = R.drawable.noun_sports_5333857),
//                    contentDescription = "Main",
//                    tint = Color.Gray.copy(alpha = 0.9f),
//                    modifier = Modifier
//                        .size(32.dp)
//                        .padding(top = 4.dp)
//                )
//                Text(
//                    text = "iQuadras",
//                    style = TextStyle(
//                        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
//                        fontSize = 24.sp,
//                        color = Color.Black.copy(alpha = 0.7f)
//                    ),
//                    modifier = Modifier.padding(start = 8.dp)
//                )
            }
        }

        // Imagem do usuário que abre o modal ao clicar
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Main",
                    tint = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier
                        .size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.1f))
                        .border(3.dp, Color.Gray.copy(alpha = 0.0f), CircleShape)
                        .clickable { showMenu = !showMenu },
                        //.clickable { showMainDialog = true }, // Abre o modal ao clicar na imagem
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(user.imageUrl) ,// painterResource(id = R.drawable.profile2), // Altere para o avatar correto
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            //.padding(top = 7.dp)
                            .fillMaxSize()
                            .graphicsLayer(alpha = 0.99f),
                        contentScale = ContentScale.Crop
                    )
                }

                // Menu dropdown que aparece ao clicar na imagem
                MaterialTheme(
                    // Define a cor de fundo do menu como vermelho
                    colorScheme = MaterialTheme.colorScheme.copy(background = Color(0xFFFAF8FF)),
                    shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20))
                ){
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier
                            .width(240.dp)
                            .background(Color.White) // color = Color(0xFFFAF8FF))

                    ) {
                        DropdownMenuItem(onClick = {
                            // Ação ao clicar em "Perfil"
                            showMenu = false
                            showDialog = true // Abre o modal de perfil
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Search Icon",
                                    tint = Color.Black.copy(alpha = 0.25f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Meu perfil",
                                    style = TextStyle(
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                                        fontSize = 16.sp,
                                        color = Color.Black.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                        DropdownMenuItem(onClick = {
                            // Ação ao clicar em "Reservas"
                            onReservationsClick()
                            showMenu = false
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Search Icon",
                                    tint = Color.Black.copy(alpha = 0.25f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Minhas reservas",
                                    style = TextStyle(
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                                        fontSize = 16.sp,
                                        color = Color.Black.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                        DropdownMenuItem(onClick = {
                            // Ação ao clicar em "Sair"
                            showMenu = false
                            onLogoffClick() // Chama a função de logoff
                        }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "Search Icon",
                                    tint = Color.Black.copy(alpha = 0.25f),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Sair",
                                    style = TextStyle(
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
                                        fontSize = 16.sp,
                                        color = Color.Black.copy(alpha = 0.7f)
                                    ),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    // Modal com opções: Reservas, Perfil, Sair
    if (showMainDialog) {
        MainOptionsDialog(
            onDismiss = { showMainDialog = false },
            onProfileClick = {
                showMainDialog = false // Fecha o modal de opções
                showDialog = true // Abre o modal de perfil
            },
            onLogoffClick = onLogoffClick
        )
    }

    // Modal de Perfil do Usuário
    if (showDialog) {
        UserProfileDialog(
            userDTO = user, // Passando o objeto User para o modal
            onDismiss = { showDialog = false },
            onDeleteClick = {
                showDialog = false
            },
            onLogoffClick

        )
    }
}

// Modal com 3 opções: Reservas, Perfil e Sair
@Composable
fun MainOptionsDialog(
    onDismiss: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoffClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Reservas",
                modifier = Modifier
                    .clickable {
                        // Ação para "Reservas"
                        onDismiss()
                    }
                    .padding(8.dp)
            )
            Text(
                text = "Perfil",
                modifier = Modifier
                    .clickable {
                        onProfileClick()
                    }
                    .padding(8.dp)
            )
            Text(
                text = "Sair",
                modifier = Modifier
                    .clickable {
                        onLogoffClick()
                    }
                    .padding(8.dp)
            )
        }
    }
}
