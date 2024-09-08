package com.example.iquadras.ui.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.iquadras.model.court.Court
import com.example.iquadras.ui.viewmodel.CourtViewModel

@Composable
fun CourtListScreen(viewModel: CourtViewModel) {
    val courts = remember { mutableStateListOf<Court>() }

    LaunchedEffect(Unit) {
        viewModel.getAllCourts { retrievedCourts ->
            courts.clear()
            courts.addAll(retrievedCourts)
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(courts) { court ->
            CourtItem(court) {
                viewModel.deleteCourt(court.id) { success ->
                    if (success) {
                        courts.remove(court)
                    }
                }
            }
        }
    }
}

@Composable
fun CourtItem(court: Court, onDelete: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Name: ${court.name}")
        Text(text = "Location: ${court.adress}")
        Text(text = "Type: ${court.type}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}
