package com.dicoding.jetcatsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dicoding.jetcatsapp.ui.screen.SplashScreen
import com.dicoding.jetcatsapp.ui.JetCatsApp
import com.dicoding.jetcatsapp.ui.screen.ProfileScreen


@Composable
fun SetupNavGraph(navController: NavHostController) {
    val navController = rememberNavController()
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
        composable(route = Screen.Favorite.route) {
            JetCatsApp(navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }

    }
}