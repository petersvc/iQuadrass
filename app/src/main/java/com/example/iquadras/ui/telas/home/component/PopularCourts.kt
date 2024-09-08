package com.example.iquadras.ui.telas.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.iquadras.R

@Composable
fun PopularCourts(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Populares",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(start = 18.dp)
                    .width(80.dp), // A linha ocupará o espaço restante
                thickness = 2.dp,
                color = Color.Gray.copy(alpha = 0.15f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(2) { // Substitua 3 pelo número de boxes que deseja exibir
                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .padding(8.dp)
                        .shadow(
                            8.dp,
                            shape = RoundedCornerShape(12.dp)
                        ) // Sombra com cantos arredondados
                        .clip(RoundedCornerShape(12.dp)) // Define o arredondamento dos cantos
                        .background(Color.White) // Cor de fundo da Box
                        .weight(1f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.quadravoley),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}