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
fun AddCourtScreen(navController: NavController, courtViewModel: CourtViewModel) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
        TextField(value = type, onValueChange = { type = it }, label = { Text("Type") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val newCourt = Court(name = name, adress = address, type = type)
            courtViewModel.saveCourt(newCourt) {
                navController.popBackStack()
            }
        }) {
            Text("Save Court")
        }
    }
}
