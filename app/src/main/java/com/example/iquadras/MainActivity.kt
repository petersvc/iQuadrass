package com.example.iquadras

import android.net.Uri
import com.google.gson.Gson
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            IquadrasTheme {
                val navController = rememberNavController()

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
                            HomeActivity(
                                modifier = Modifier.padding(innerPadding),
                                onLogoffClick = {
                                    navController.navigate("login")
                                },
                                user = user,
                                onCourtClick = { court ->
                                    val courtJson = Uri.encode(Gson().toJson(court))
                                    val userJsonForCourt = Uri.encode(Gson().toJson(user))
                                    navController.navigate("courtview/$courtJson/$userJsonForCourt")
                                }
                            )
                        }
                        // Definindo a rota courtview/{courtJson}/{userJsonForCourt}
                        composable("courtview/{courtJson}/{userJsonForCourt}") { backStackEntry ->
                            val courtJson = backStackEntry.arguments?.getString("courtJson")
                            val userJsonForCourt = backStackEntry.arguments?.getString("userJsonForCourt")

                            // Desserializando o Court e o User
                            val court = Gson().fromJson(courtJson, Court::class.java)
                            val user = Gson().fromJson(userJsonForCourt, User::class.java)

                            // Passar ambos para a CourtView
                            CourtView(court = court, user = user)
                        }

                    }

                }
            }
        }
    }
}


