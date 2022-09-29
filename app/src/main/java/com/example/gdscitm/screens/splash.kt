package com.example.gdscitm.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gdscitm.R
import kotlinx.coroutines.delay

@Composable
fun SplashAnimation() {
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
        // TODO: add navigation 
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
                .width(100.dp)
                .height(100.dp)
                .size(80.dp)
                .alpha(alpha = alphaval)
                .clip(
                    CircleShape
                ),
        )
    }
    }
}


@Preview
@Composable
fun SplashPrev() {
    Splash(1f)
}