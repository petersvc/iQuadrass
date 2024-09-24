//package com.example.iquadras.ui.telas
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.iquadras.model.court.Court
//
//@Composable
//fun ReservationsScreen(
//    courts: List<Court>,
//    onCourtClick: (Court) -> Unit
//) {
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth()
//    ) {
//        Text(
//            text = "Quadras Reservadas",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        if (courts.isEmpty()) {
//            Text("Você ainda não reservou nenhuma quadra.")
//        } else {
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(courts.size) { index ->
//                    val court = courts[index]
//                    ReservedCourtItem(court = court, onClick = { onCourtClick(court) })
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ReservedCourtItem(
//    court: Court,
//    onClick: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        onClick = onClick
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Quadra: ${court.name}", fontWeight = FontWeight.Bold)
//            Text(text = "Localização: ${court.latitude}, ${court.longitude}")
//            Text(text = "Preço: R$ ${court.price}")
//            //Text(text = "Esportes: ${court.sports.joinToString(", ")}")
//        }
//    }
//}
