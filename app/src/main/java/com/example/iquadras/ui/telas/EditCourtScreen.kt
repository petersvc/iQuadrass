package com.example.iquadras.ui.telas

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iquadras.model.court.Court
import com.example.iquadras.ui.viewmodel.CourtViewModel

@Composable
fun EditCourtScreen(navController: NavController, courtViewModel: CourtViewModel, courtId: String) {
    var court by remember { mutableStateOf<Court?>(null) }

    // Carregar os dados da quadra existente
    LaunchedEffect(courtId) {
        courtViewModel.findCourtById(courtId) { fetchedCourt ->
            court = fetchedCourt
        }
    }

    // Verificar se a quadra foi carregada
    if (court != null) {
        var name by remember { mutableStateOf(court!!.name) }
        var address by remember { mutableStateOf(court!!.adress) }
        var type by remember { mutableStateOf(court!!.type) }

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
            TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
            TextField(value = type, onValueChange = { type = it }, label = { Text("Type") })

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val updatedCourt = court!!.copy(name = name, adress = address, type = type)
                courtViewModel.saveCourt(updatedCourt) {
                    navController.popBackStack()
                }
            }) {
                Text("Save Changes")
            }
        }
    } else {
        // Mostrar uma mensagem de carregamento ou de erro
        Text("Loading court details...")
    }
}
