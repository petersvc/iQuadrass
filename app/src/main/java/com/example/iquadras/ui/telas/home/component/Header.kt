package com.example.iquadras.ui.telas.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.iquadras.R
import com.example.iquadras.model.user.User



@Composable
fun Header(
    onLogoffClick: () -> Unit,
    modifier: Modifier = Modifier,
    user: User, // Recebendo o usuário logado

) {
    var showDialog by remember { mutableStateOf(false) } // Controla se o modal de perfil deve ser exibido

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.noun_sports_5333857),
                    contentDescription = "Main",
                    tint = Color.Gray.copy(alpha = 0.9f),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 4.dp)
                )
                Text(
                    text = "iQuadras",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Imagem do usuário que abre o modal ao clicar
        Column {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .clickable { showDialog = true }, // Abre o modal ao clicar na imagem
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile2), // Altere para o avatar correto
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.99f),
                    contentScale = ContentScale.Crop
                )
            }
        }
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

