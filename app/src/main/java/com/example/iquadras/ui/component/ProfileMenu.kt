package com.example.iquadras.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExitToApp
import com.example.iquadras.R

@Composable
fun ProfileDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onProfileClick: () -> Unit,
    onReservationsClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(onClick = onProfileClick) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null)
            Text("My Profile")
        }
        DropdownMenuItem(onClick = onReservationsClick) {
            Icon(imageVector = Icons.Default.Event, contentDescription = null)
            Text("My Reservations")
        }
        DropdownMenuItem(onClick = onSignOutClick) {
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
            Text("Sign Out")
        }
    }
}

@Composable
fun ProfileIconDropdown() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize()
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(id = R.drawable.profile),  // Não sei qual é a foto, mas é depois e só alterar aí
                contentDescription = "Profile Icon"
            )
        }

        ProfileDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            onProfileClick = {
                // Navegar para a tela de perfil
                expanded = false
            },
            onReservationsClick = {
                // Navegar para a tela de reservas de quadras
                expanded = false
            },
            onSignOutClick = {
                // Lógica de logout
                expanded = false
            }
        )
    }
}
