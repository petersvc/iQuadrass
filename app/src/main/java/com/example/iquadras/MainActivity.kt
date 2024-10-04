package com.example.iquadras

import BookingsScreen
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import com.google.gson.Gson
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.iquadras.model.court.Court
import com.example.iquadras.model.user.DTOUser
import com.example.iquadras.model.user.User
import com.example.iquadras.ui.component.CourtView
import com.example.iquadras.ui.telas.TelaCadastro
import com.example.iquadras.ui.telas.TelaLogin
import com.example.iquadras.ui.telas.home.HomeActivity
import com.example.iquadras.ui.theme.IquadrasTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        //val locationDatabase = UserLocationDatabase.getInstance(this) // Inicializando o banco de dados Room

        setContent {
            IquadrasTheme {
                val navController = rememberNavController()
                var currentLocation by remember { mutableStateOf<Location?>(null) }
                val context = LocalContext.current

                // Obtendo a permissão e localização
                requestLocationPermission()?.let {
                    currentLocation = it

		    /// Salvando a localização no banco de dados quando for obtida
                    currentLocation?.let {
                        //saveLocationInDatabase(locationDatabase, it)
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            TelaLogin(
                                modifier = Modifier.padding(innerPadding),
                                onSignInClick = { user: DTOUser ->
                                    val userJson = Uri.encode(Gson().toJson(user))
                                    navController.navigate("main/$userJson")
                                },
                                onSignUpClick = {
                                    navController.navigate("signup")
                                }
                            )
                        }
                        composable("signup") {
                            TelaCadastro(
                                modifier = Modifier.padding(innerPadding),
                                onSignUpClick = {
                                    navController.navigate("login")
                                },
                                onSignInClick = {
                                    navController.navigate("login")
                                }
                            )
                        }
                        composable("main/{userJson}") { backStackEntry ->
                            val userJson = backStackEntry.arguments?.getString("userJson")
                            val user = Gson().fromJson(userJson, User::class.java)

                            // Passando a localização atual para a HomeActivity
                            HomeActivity(
                                modifier = Modifier.padding(innerPadding),
                                onLogoffClick = {
                                    navController.navigate("login")
                                },
                                user = user,
                                currentLocation = currentLocation, // Passando a localização
                                onCourtClick = { court ->
                                    val courtJson = Uri.encode(Gson().toJson(court))
                                    val userJsonForCourt = Uri.encode(Gson().toJson(user))
                                    navController.navigate("courtview/$courtJson/$userJsonForCourt")
                                },
                                onReservationsClick = {
                                    val userJson2 = Uri.encode(Gson().toJson(user))
                                    navController.navigate("reservations/$userJson2")
                                }
                            )
                        }
                        composable("courtview/{courtJson}/{userJsonForCourt}") { backStackEntry ->
                            val courtJson = backStackEntry.arguments?.getString("courtJson")
                            val userJsonForCourt = backStackEntry.arguments?.getString("userJsonForCourt")

                            val court = Gson().fromJson(courtJson, Court::class.java)
                            val user = Gson().fromJson(userJsonForCourt, User::class.java)

                            // Passando a localização atual para a CourtView
                            CourtView(court = court, user = user, currentLocation = currentLocation)
                        }

                        composable("reservations/{userJson2}") { backStackEntry ->
                            val userJson2 = backStackEntry.arguments?.getString("userJson2")
                            val user = Gson().fromJson(userJson2, User::class.java)
                            println(user.id)
                            BookingsScreen(
                                user = user,// Função que busca as reservas do usuário
                                onReservationsClick = {
                                    val userJson2 = Uri.encode(Gson().toJson(user))
                                    navController.navigate("reservations/$userJson2")
                                },
                                onLogoffClick = {
                                    navController.navigate("login")
                                },
                            )
                        }

                    }

                }
            }
        }
    }
}

// Função para salvar a localização no banco de dados Room
//fun saveLocationInDatabase(database: UserLocationDatabase, location: Location) {
//    val userLocation = UserLocation(
//        latitude = location.latitude,
//        longitude = location.longitude,
//        timestamp = System.currentTimeMillis()
//    )
//    // Lançando uma coroutine para salvar no banco de dados
//    kotlinx.coroutines.GlobalScope.launch(Dispatchers.IO) {
//        database.userLocationDao().insertLocation(userLocation)
//    }
//}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun requestLocationPermission(): Location? {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)
    var loc by remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    when {
        permissionState.status.isGranted -> {
            GetCurrentLocation(context) { location ->
                location?.let {
                    loc = location
                }
            }
        }
    }
    return loc
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
        } else {
            onLocationReceived(null)
        }
    }
}

