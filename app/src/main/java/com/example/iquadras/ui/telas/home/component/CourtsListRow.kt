//package com.example.iquadras.ui.telas.home.component
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color.Companion.Black
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberAsyncImagePainter
//import com.example.iquadras.R
//import com.example.iquadras.model.court.Court
//import com.example.iquadras.ui.telas.themeColor
//
//@Composable
//fun CourtsListRow(courts: List<Court>) {
//    LazyRow() {
//        items(courts) { court ->
//            CourtCardRow2(court = court)
//        }
//    }
//}
//
//@Composable
//fun CourtCardRow2(court: Court) {
//    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = Color.Transparent
//        ),
//        modifier = Modifier
//            .padding(16.dp)
//            .width(230.dp)
//            .height(270.dp)
//            .clip(RoundedCornerShape(8.dp)),
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Image(
//                painter = rememberAsyncImagePainter("https://images7.alphacoders.com/134/1349294.jpeg"),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(8.dp))
//                    .padding(12.dp),
//                    //.background(Color.White)
//                    //.alpha(0.2f),
//                contentScale = ContentScale.Crop,
//            )
//
//            Column(
//                verticalArrangement = Arrangement.Bottom,
//                modifier = Modifier
//                    .fillMaxSize() // Add transparency overlay
//                    .padding(horizontal = 12.dp, vertical = 12.dp)
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Column() {
//                        Row(verticalAlignment = Alignment.CenterVertically)
//                        {
//                            Icon(
//                                imageVector = Icons.Default.LocationOn,
//                                contentDescription = null,
//                                tint
//                            = themeColor.copy(alpha = 0.8f),
//                            modifier = Modifier.size(12.dp)
//                            )
//                            Text(
//                                text = "Cabedelo, Intermares",
//                                color = themeColor.copy(alpha = 0.8f),
//                                style = MaterialTheme.typography.bodyMedium, fontSize = 12.sp,
//                                modifier = Modifier.padding(start = 4.dp)
//                            )
//                        }
//                    }
//                    // ... rest of your information content
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//                Row {
//                    Text(
//                        text = court.name,
//                        color = Color.White, // Text color for better contrast
//                        style = MaterialTheme.typography.titleMedium, fontSize = 14.sp,
//                    )
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//                Row {
//                    Text(
//                        text = "R$ " + court.price.toString() + "/hora",
//                        color = Color.White, // Text color for better contrast
//                        style = MaterialTheme.typography.titleMedium, fontSize = 16.sp,
//                    )
//                }
//
//            }
//        }
//    }
//}
//
//@Composable
//fun CourtCardRow(court: Court) {
//    Card(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(8.dp)),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White.copy(alpha = 0.8f), //Card background color
//        ),
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//            ) {
//                Box(
//                    modifier = Modifier
//                        .width(90.dp)
//                        .height(90.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.quadravoley),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxHeight(),
//                        alignment = Alignment.CenterStart,
//                        contentScale = ContentScale.Crop,
//                    )
//                }
//            }
//            Column(
//                modifier = Modifier.padding(16.dp),
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Column() {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Icon(
//                                imageVector = Icons.Default.LocationOn,
//                                contentDescription = null,
//                                tint = themeColor.copy(alpha = 0.8f),
//                                modifier = Modifier.size(12.dp)
//                            )
//                            Text(
//                                text = "Cabedelo, Intermares - 4km",
//                                color = themeColor.copy(alpha = 0.8f),
//                                style = MaterialTheme.typography.bodyMedium, fontSize = 12.sp,
//                                modifier = Modifier.padding(start = 4.dp)
//                            )
//                        }
//                    }
////                    Column(Modifier.weight(1f)) {
////                        val sports = court.sports.joinToString(", ").lowercase()
////                        Text(
////                            text = sports,
////                            color = themeColor.copy(alpha = 0.7f),
////                            style = MaterialTheme.typography.bodyMedium,
////                        )
////                    }
//
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//                Row {
//                    Text(
//                        text = court.name,
//                        color = Black.copy(alpha = 0.5f),
//                        style = MaterialTheme.typography.titleMedium, fontSize = 14.sp,
//                    )
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//                Row {
//                    Text(
//                        text = "R$ " + court.price.toString() + "/hora",
//                        color = Black.copy(alpha = 0.6f),
//                        style = MaterialTheme.typography.titleMedium, fontSize = 16.sp,
//                    )
//                }
//
//            }
//        }
//    }
//}
