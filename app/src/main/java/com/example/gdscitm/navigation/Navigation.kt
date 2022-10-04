package com.example.gdscitm.navigation

sealed class Navigation(val route :String){
    object Splash: Navigation(route = "splashscreen")
    object Login: Navigation(route = "loginscreen")
}
