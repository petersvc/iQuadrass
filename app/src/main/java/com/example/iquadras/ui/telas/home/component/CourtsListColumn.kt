package com.example.iquadras.ui.telas.home.component

import android.annotation.SuppressLint
import android.location.Location
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.iquadras.model.court.Court
import com.example.iquadras.ui.component.showDistanceToTarget
import com.example.iquadras.ui.telas.themeColor

@Composable
fun CourtsListColumn(courts: List<Court>, onCourtClick: (Court) -> Unit, currentLocation: Location?) {
    LazyColumn () {
        items(courts) { court ->
            CourtCardColumn(court = court, onCourtClick = onCourtClick, currentLocation = currentLocation)
        }
    }
}

@Composable
fun CourtCardColumn(court: Court, onCourtClick: (Court) -> Unit, currentLocation: Location?) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(80.dp))
            .clickable { onCourtClick(court) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.0f),
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(90.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(80.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(court.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxHeight(),
                        alignment = Alignment.CenterStart,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = themeColor.copy(alpha = 0.8f),
                                modifier = Modifier.size(12.dp)
                            )
                            val adress = court.address.split(",")
                            val city = adress[3].trim()
                            val district = adress[2].trim()
                            val distanceInKm = showDistanceToTarget(currentLocation, court.latitude, court.longitude)
                            Text(
                                text = "$city, $district - $distanceInKm",
                                color = themeColor.copy(alpha = 0.8f),
                                style = MaterialTheme.typography.bodyMedium, fontSize = 13.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Text(
                        text = court.name,
                        color = Black.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.titleMedium, fontSize = 14.sp,
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Text(
                        text = "R$ " + formatPrice(court.price) + "/hora",
                        color = Black.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.titleMedium, fontSize = 16.sp,
                    )
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatPrice(price: Double): String {
    val formattedPrice = String.format("%.2f", price)

    // Verifica se os dois primeiros dígitos após o ponto são zeros
    return if (formattedPrice.split(".")[1].startsWith("0")) {
        formattedPrice.split(".")[0]
    } else {
        formattedPrice
    }
}