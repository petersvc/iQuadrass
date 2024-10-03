//package com.example.iquadras.ui.telas.home.component
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.HorizontalDivider
//
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import com.example.iquadras.model.court.Court
//
//@Composable
//fun AllCourts(modifier: Modifier, courts: List<Court>) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start,
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//    ) {
//        Text(
//            text = "Lista",
//            style = MaterialTheme.typography.bodyMedium,
//        )
//        HorizontalDivider(
//            modifier = Modifier
//                .padding(start = 18.dp)
//                .width(80.dp),
//            thickness = 2.dp,
//            color = Color.Gray.copy(alpha = 0.15f)
//        )
//    }
//
//    val nearbyCourts = courts.filter { it.capacity > 5 } // Filter courts with capacity > 10
//    // CourtsListColumn(nearbyCourts)
//}