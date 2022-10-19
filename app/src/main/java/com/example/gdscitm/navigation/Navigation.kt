package com.example.gdscitm.navigation

sealed class Navigation(val route :String){
    object Splash: Navigation(route = "splashscreen")
    object userType: Navigation(route = "usertypeScreen")
    object Login: Navigation(route = "loginscreen")
    object StudentLogin: Navigation(route = "studentloginscreen")
    object Home: Navigation(route = "homescreen")
    object Attendance: Navigation(route = "attendancescreen")
    object Attendancelistscreen: Navigation(route = "Attendancelistscreen/{id}/{tempid}/{period}/{type}")

}
