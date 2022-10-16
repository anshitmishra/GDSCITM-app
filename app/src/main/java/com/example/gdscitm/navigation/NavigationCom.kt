package com.example.gdscitm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gdscitm.Screen.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.Splash.route){
        composable(route = Navigation.Splash.route){
            SplashAnimation(navController = navController)
        }
        composable(route = Navigation.userType.route){
            UserType(navController = navController)
        }
        composable(route = Navigation.Login.route){
            Login(navController = navController)
        }
        composable(route = Navigation.StudentLogin.route){
            StudentLogin(navController = navController)
        }
        composable(route = Navigation.Home.route){
            MainScreen(navController = navController)
        }
        composable(route = Navigation.Attendance.route){
            AttendanceMain(navController = navController)
        }
        composable(route = Navigation.Attendancelistscreen.route, arguments = listOf(navArgument("id"){
            type = NavType.StringType
        },navArgument("type"){
            type = NavType.StringType
        })){
            saveAttendance(navController = navController,it.arguments?.getString("id").toString(),it.arguments?.getString("type").toString())
        }

    }
}