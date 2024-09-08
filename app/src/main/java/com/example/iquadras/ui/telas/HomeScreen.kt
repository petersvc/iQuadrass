package com.example.iquadras.ui.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column {
        Button(onClick = { navController.navigate("courtList") }) {
            Text("Go to Court List")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("addCourt") }) {
            Text("Add New Court")
        }
    }
}
