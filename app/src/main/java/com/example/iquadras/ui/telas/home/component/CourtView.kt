package com.example.iquadras.ui.telas.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.R
import com.example.iquadras.model.court.Court

@Composable
fun CourtView(court: Court) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Imagem principal
        Image(
            painter = painterResource(id = R.drawable.quadravoley),  // Substitua com a imagem correta
            contentDescription = "Court Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nome da quadra e preço
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = court.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 24.sp
            )

            Text(
                text = "$${court.price}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                fontSize = 24.sp,
                color = Color(0xFF4169E1)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Endereço
        Text(
            text = court.adress,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Distância e outros detalhes (ícones e informações)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Distance",
                    tint = Color(0xFF87CEEB),
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "6 km", // Exemplo, pode ser dinâmico
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.logoapp),  // Ícone de usuários
                    contentDescription = "Users",
                    tint = Color(0xFF87CEEB),
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "${court.capacity} users",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.logoapp),  // Ícone de esportes
                    contentDescription = "Sports Type",
                    tint = Color(0xFF87CEEB),
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = court.type,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "${court.score}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Descrição da quadra
        Text(
            text = court.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão para reservar a quadra
        Button(
            onClick = { /* Ação de reserva */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4169E1),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Reservation Court", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
