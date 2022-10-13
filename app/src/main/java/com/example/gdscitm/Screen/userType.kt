package com.example.gdscitm.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gdscitm.R
import com.example.gdscitm.global.mainGlobal
import com.example.gdscitm.navigation.Navigation

@Composable
fun UserType(navController : NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.splash_type), contentDescription = "splash image", modifier = Modifier
                .fillMaxWidth()
                .size(400.dp))
            Column(modifier = Modifier.padding(50.dp,10.dp), horizontalAlignment = Alignment.CenterHorizontally ) {
                Text(text = "Welcome To GDSCITM ", fontSize = 25.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.padding(0.dp,10.dp))
                Button(onClick = {
                    mainGlobal.studentType = "T"
                    navController.navigate(Navigation.Login.route)
                                 }, modifier = Modifier
                    .width(270.dp),
                    colors = ButtonDefaults.buttonColors( Color(87, 85, 217))) {
                    Text(text = "Teacher", color = Color(0xFFFFFFFF))
                }
                Button(onClick = {
                    mainGlobal.studentType = "S"
                    navController.navigate(Navigation.StudentLogin.route)
                }, modifier = Modifier.width(270.dp), colors = ButtonDefaults.buttonColors( Color.Transparent), border = BorderStroke(2.dp,Color(87, 85, 217))) {
                    Text(text = "Student", color = Color(87, 85, 217))
                }
            }
        }
    }

}




@Preview
@Composable
fun PrevUserType() {
    UserType(rememberNavController())
}