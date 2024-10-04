package com.example.iquadras.ui.telas.home

import android.location.Location
<<<<<<< HEAD
=======
import androidx.compose.foundation.border
>>>>>>> main
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
<<<<<<< HEAD
import androidx.compose.foundation.shape.RoundedCornerShape
=======
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
>>>>>>> main
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
<<<<<<< HEAD
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.model.court.Court
import com.example.iquadras.model.restClient.RetrofitClient
=======
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iquadras.model.court.Court
import com.example.iquadras.restClient.RetrofitClient
>>>>>>> main
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.home.component.CourtsListColumn
import com.example.iquadras.ui.telas.home.component.Header
import com.example.iquadras.ui.telas.home.component.SubHeader
import com.example.iquadras.ui.telas.themeColor
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivity(
    modifier: Modifier = Modifier,
    onLogoffClick: () -> Unit,
    onCourtClick: (Court) -> Unit,
    user: User,
    onReservationsClick: () -> Unit,
    currentLocation: Location?
) {
    val scope = rememberCoroutineScope()
    val courts = remember { mutableStateListOf<Court>() }
<<<<<<< HEAD
=======
    var filteredCourts by remember { mutableStateOf(listOf<Court>()) }
    val focusManager = LocalFocusManager.current
>>>>>>> main

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val courtsFetched = getAllCourts()
            courts.clear()
            courts.addAll(courtsFetched)
<<<<<<< HEAD
=======
            filteredCourts = courtsFetched
>>>>>>> main
//            courtDAO.getAll(callback = { courtsFetched ->
//                courts.clear()
//                courts.addAll(courtsFetched)
//            })
        }
    }

    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Header(onLogoffClick = onLogoffClick, modifier = Modifier, user = user, onReservationsClick = onReservationsClick)

<<<<<<< HEAD
        SubHeader()

        // Spacer(modifier = Modifier.height(16.dp))

        // RequestLocationPermission()

        Spacer(modifier = Modifier.height(8.dp))

=======
        Spacer(modifier = Modifier.height(8.dp))

        SubHeader("Encontre a sua ", "quadra.")

>>>>>>> main
        var searchQuery by remember { mutableStateOf("") }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
<<<<<<< HEAD
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
=======
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp),
>>>>>>> main
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = searchQuery,
<<<<<<< HEAD
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .height(44.dp),
=======
                onValueChange = { query ->
                    searchQuery = query
                    // Atualiza as quadras filtradas com base na pesquisa
                    filteredCourts = courts.filter { court ->
                        court.name.contains(query, ignoreCase = true)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
                    .height(64.dp),
>>>>>>> main
                label = {
                    Text(
                        text = "Buscar quadras",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.6f)

                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
<<<<<<< HEAD
                        tint = Color.Black.copy(alpha = 0.4f)
=======
                        tint = Color.Black.copy(alpha = 0.4f),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp)
>>>>>>> main
                    )
                },
                shape = RoundedCornerShape(32.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = themeColor.copy(alpha = 0.8f),
                    unfocusedBorderColor = Color.Black.copy(alpha = 0.1f),
<<<<<<< HEAD
                )
            )
            IconButton(
                onClick = { /* Ação do ícone de filtros */ },
                modifier = Modifier.size(56.dp).weight(1f).fillMaxHeight()
=======
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus() // Remove o foco ao pressionar "Done"
                    }
                ),
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = { /* Ação do ícone de filtros */ },
                modifier = Modifier
                    .size(56.dp)
                    .fillMaxHeight()
                    .border(
                        width = 1.dp,
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(64.dp)
                )
>>>>>>> main
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtros Icon",
<<<<<<< HEAD
                    tint = Color.Black.copy(alpha = 0.4f)
=======
                    tint = Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier.size(28.dp)
>>>>>>> main

                )
            }
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
        ) {
<<<<<<< HEAD
            CourtsListColumn(courts, onCourtClick, currentLocation)
        }



    }
}

//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun RequestLocationPermission() {
//    val context = LocalContext.current
//    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
//    var locationText by remember { mutableStateOf("Aguardando localização...") }
//
//    LaunchedEffect(Unit) {
//        permissionState.launchPermissionRequest()
//    }
//
//    when {
//        permissionState.status.isGranted -> {
//            //Text(text = "Permissão concedida. Buscando localização...")
//            GetCurrentLocation(context) { location ->
//                location?.let {
//                    locationText = "Latitude: ${it.latitude}, Longitude: ${it.longitude}"
//                }
//            }
//        }
//        permissionState.status.shouldShowRationale -> {
//            Text(text = "Precisamos da permissão para mostrar sua localização.")
//        }
//        else -> {
//            Text(text = "Permissão negada permanentemente.")
//        }
//    }
//
//    Text(text = "Localização: $locationText")
//}

//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun RequestLocationPermission(onLocationReceived: (Location?) -> Unit) {
//    val context = LocalContext.current
//    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
//
//    LaunchedEffect(Unit) {
//        permissionState.launchPermissionRequest()
//    }
//
//    when {
//        permissionState.status.isGranted -> {
//            GetCurrentLocation(context) { location ->
//                onLocationReceived(location)
//            }
//        }
//        else -> {
//            onLocationReceived(null)
//        }
//    }
//}
//
//@Composable
//fun GetCurrentLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
//    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//
//    LaunchedEffect(Unit) {
//        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
//                .addOnSuccessListener { location: Location? ->
//                    onLocationReceived(location)
//                }
//        } else {
//            onLocationReceived(null)
//        }
//    }
//}


=======
            CourtsListColumn(courts = filteredCourts, onCourtClick, currentLocation)
        }

    }
}

>>>>>>> main
suspend fun getAllCourts(): List<Court> {
    return withContext(Dispatchers.IO) {
        RetrofitClient.courtService.getAllCourts()
    }
}
