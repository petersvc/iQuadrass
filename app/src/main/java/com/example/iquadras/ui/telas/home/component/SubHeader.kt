package com.example.iquadras.ui.telas.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.ui.telas.themeColor

@Composable
fun SubHeader(text: String, text2: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Light,
                                fontSize = 24.sp,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        ) {
                            append(text)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = themeColor.copy(alpha = 0.99f)
                            )
                        ) {
                            append(text2)
                        }
                    },
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .width(320.dp)
                )
            }
        }
//        Column {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "Filtros",
//                    style = TextStyle(
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 16.sp,
//                        color = Color.Black.copy(alpha = 0.6f)
//                    ),
//                    modifier = Modifier.padding(start = 8.dp)
//                )
//                Icon(
//                    imageVector = Icons.Outlined.CalendarMonth,
//                    contentDescription = "Main",
//                    tint = Color.Black.copy(alpha = 0.5f),
//                    modifier = Modifier
//                        .size(28.dp)
//                        .padding(start = 0.dp),
//                )
//            }
//        }
//
    }
}