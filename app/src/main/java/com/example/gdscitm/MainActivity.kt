package com.example.gdscitm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.gdscitm.navigation.Navigation
import com.example.gdscitm.ui.theme.GTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            Navigation()


        }
    }
}


@Composable
fun Greeting(name : String) {

    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GTheme {
        Greeting("Android")
    }
}