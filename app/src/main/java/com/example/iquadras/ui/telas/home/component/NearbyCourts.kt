package com.example.iquadras.ui.telas.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.iquadras.model.court.Court

@Composable
fun NearbyCourts(modifier: Modifier = Modifier,
                 courts: List<Court>,
                 onCourtClick: (Court) -> Unit ){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Próximas à você",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 8.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(start = 18.dp)
                .width(80.dp),
            thickness = 2.dp,
            color = Color.Gray.copy(alpha = 0.15f)
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        //Carrega sob demanda à medida que o usuário rola na tela
        LazyColumn(Modifier.weight(1f)) {
            items(courts) { court ->
                Box(modifier = Modifier){
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White, //Card background color
                            contentColor = Color.White  //Card content color,e.g.text
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .shadow(
                                8.dp,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                onCourtClick(court)
                            }
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
                                    .padding(vertical = 10.dp)
                            )

                            // Ícone e Preço
                            Row(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Place,
                                        contentDescription = "Main",
                                        tint = Color.Gray.copy(alpha = 0.5f),
                                        modifier = Modifier
                                            .size(16.dp)
                                    )
                                    Text(
                                        text = court.name,
                                        style = MaterialTheme.typography.bodyLarge
                                            .copy(color = Color.Gray.copy(alpha = 0.8f)),
                                        modifier = Modifier.padding(start = 3.dp)
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "R$ " + court.price.toString(),
                                        style = MaterialTheme.typography.bodyLarge
                                            .copy(color = Color.Gray.copy(alpha = 0.8f)),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(0.dp))
                            Row(modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 12.dp)
                                .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Person,
                                            modifier = Modifier.size(18.dp),
                                            contentDescription = "main",
                                            tint = Color.Gray.copy(alpha = 0.8f)
                                        )
                                        Text(
                                            text = court.capacity.toString() + " pessoas",
                                            style = MaterialTheme.typography.bodySmall
                                                .copy(color = Color.Gray.copy(alpha = 0.8f)),
                                            modifier = Modifier.padding(start = 3.dp, top = 1.dp)
                                        )
                                    }

                                }

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            modifier = Modifier.size(18.dp),
                                            contentDescription = "main",
                                            tint = Color.Gray.copy(alpha = 0.8f)
                                        )
                                        Text(
                                            text = court.type,
                                            style = MaterialTheme.typography.bodySmall
                                                .copy(color = Color.Gray.copy(alpha = 0.8f)),
                                            modifier = Modifier.padding(start = 3.dp, top = 1.dp)
                                        )
                                    }

                                }

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            modifier = Modifier.size(18.dp),
                                            contentDescription = "main",
                                            tint = Color.Gray.copy(alpha = 0.8f)
                                        )
                                        Text(
                                            text = court.score.toString(),
                                            style = MaterialTheme.typography.bodySmall
                                                .copy(color = Color.Gray.copy(alpha = 0.8f)),
                                            modifier = Modifier.padding(start = 3.dp, top = 1.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}