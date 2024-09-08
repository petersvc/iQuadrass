package com.example.iquadras

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
import com.example.iquadras.model.user.User
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
                                onSignInClick = { user: User ->
                                    val userJson = Gson().toJson(user)
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
                                user = user
                            )
                        }

                    }

                }
            }
        }
    }
}
