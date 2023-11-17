package com.dicoding.jetcatsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dicoding.jetcatsapp.ui.screen.SplashScreen
import com.dicoding.jetcatsapp.ui.JetCatsApp

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            JetCatsApp(navController)
        }
    }
}