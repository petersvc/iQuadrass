package com.example.iquadras.ui.telas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iquadras.ui.viewmodel.CourtViewModel

@Composable
fun AppNavigator(navController: NavHostController, courtViewModel: CourtViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("courtList") { CourtListScreen(viewModel = courtViewModel) }
        composable("addCourt") { AddCourtScreen(navController, courtViewModel) }
        composable("editCourt/{courtId}") { backStackEntry ->
            val courtId = backStackEntry.arguments?.getString("courtId")
            if (courtId != null) {
                EditCourtScreen(navController, courtViewModel, courtId)
            }
        }
    }
}

