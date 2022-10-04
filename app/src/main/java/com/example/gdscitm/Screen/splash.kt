package com.example.gdscitm.Screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gdscitm.R
import com.example.gdscitm.navigation.Navigation
import kotlinx.coroutines.delay

@Composable
fun SplashAnimation(navController : NavHostController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(4000)
        // done: add navigation
        navController.navigate(Navigation.Login.route){
            popUpTo(Navigation.Splash.route){
                inclusive = true
            }
        }
    }
    Splash(alphaval =alphaAnim.value )
}

@Composable
fun Splash(alphaval:Float) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
                modifier =
                Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .size(130.dp)
                    .alpha(alpha = alphaval)
                    .clip(
                        CircleShape
                    ),
            )
            Text(
                text = "This app is made by anshit mishra",
                fontSize = 12.sp,
                color = Color(0, 0, 0, 200),
                modifier = Modifier.alpha(alpha = alphaval)
            )
        }
    }
}


@Preview
@Composable
fun SplashPrev() {
    Splash(1f)
}