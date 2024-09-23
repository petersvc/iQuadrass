package com.example.iquadras.ui.telas.home

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.iquadras.model.court.Court
import com.example.iquadras.model.restClient.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.telas.home.component.CourtsListColumn
import com.example.iquadras.ui.telas.home.component.Header
import com.example.iquadras.ui.telas.home.component.SubHeader
import com.example.iquadras.ui.telas.themeColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivity(modifier: Modifier = Modifier, onLogoffClick: () -> Unit, user: User) {
    val scope = rememberCoroutineScope()
    val courts = remember { mutableStateListOf<Court>() }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            val courtsFetched = getAllCourts()
            courts.clear()
            courts.addAll(courtsFetched)
//            courtDAO.getAll(callback = { courtsFetched ->
//                courts.clear()
//                courts.addAll(courtsFetched)
//            })
        }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {

        Header(onLogoffClick = onLogoffClick, modifier = Modifier, user = user)

        SubHeader()

        // Spacer(modifier = Modifier.height(16.dp))

        // RequestLocationPermission()

        Spacer(modifier = Modifier.height(8.dp))

        var searchQuery by remember { mutableStateOf("") }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
                    .height(44.dp),
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
                        tint = Color.Black.copy(alpha = 0.4f)
                    )
                },
                shape = RoundedCornerShape(32.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = themeColor.copy(alpha = 0.8f),
                    unfocusedBorderColor = Color.Black.copy(alpha = 0.1f),
                )
            )
            IconButton(
                onClick = { /* Ação do ícone de filtros */ },
                modifier = Modifier.size(56.dp).weight(1f).fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtros Icon",
                    tint = Color.Black.copy(alpha = 0.4f)

                )
            }
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
        ) {
//            FeaturedCourts(modifier, courts)
//            NearbyCourts(modifier, courts)
            CourtsListColumn(courts)
        }



    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission() {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var locationText by remember { mutableStateOf("Aguardando localização...") }

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    when {
        permissionState.status.isGranted -> {
            //Text(text = "Permissão concedida. Buscando localização...")
            GetCurrentLocation(context) { location ->
                location?.let {
                    locationText = "Latitude: ${it.latitude}, Longitude: ${it.longitude}"
                }
            }
        }
        permissionState.status.shouldShowRationale -> {
            Text(text = "Precisamos da permissão para mostrar sua localização.")
        }
        else -> {
            Text(text = "Permissão negada permanentemente.")
        }
    }

    //Text(text = "Localização: $locationText")
}

@Composable
fun GetCurrentLocation(context: Context, onLocationReceived: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location: Location? ->
                    onLocationReceived(location)
                }
        }
    }
}

suspend fun getAllCourts(): List<Court> {
    return withContext(Dispatchers.IO) {
        RetrofitClient.courtService.getAllCourts()
    }
}
