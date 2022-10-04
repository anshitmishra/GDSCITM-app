package com.example.gdscitm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gdscitm.Screen.Login
import com.example.gdscitm.Screen.SplashAnimation

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.Splash.route){
        composable(route = Navigation.Splash.route){
            SplashAnimation(navController = navController)
        }

        composable(route = Navigation.Login.route){
            Login(navController = navController)
        }
    }
}