package com.example.gdscitm.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gdscitm.R

@Composable
fun MainScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
          Scaffold(
              topBar = {
                       TopBar()
              },
              bottomBar = {
                  BottomBar()
              },
              content = {
                  Text(text = "helloo")
              }
          )
        }
}

@Composable
fun BottomBar() {
    BottomAppBar(backgroundColor = Color.Transparent, elevation = 1.dp, modifier = Modifier
        .fillMaxWidth() ) {
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = ({ Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "home"
        )}), label = ({ Text(text = "home", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
        BottomNavigationItem(selected = true, onClick = { /*TODO*/ }, icon = ({ Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "attendance",
        )}), label = ({ Text(text = "attendance", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
        BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = ({ Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "setting"
        )}), label = ({ Text(text = "setting", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
    }
}

@Composable
fun TopBar() {
    TopAppBar(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(), backgroundColor = Color.Transparent, elevation = 0.dp) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.fillMaxWidth(0.8F)) {
                Text(text = "Hii Anshit,", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily =  FontFamily(
                    Font(R.font.ubuntu_medium)
                ))
                Text(text = "Today Schedule", fontWeight = FontWeight.Medium, fontSize = 15.sp,fontFamily =  FontFamily(
                    Font(R.font.ubuntu_medium)
                ))
            }
            Column(modifier = Modifier.fillMaxWidth(0.8F)) {
                Image(
                    painter = painterResource(id = R.drawable.test_user),
                    contentDescription = "login",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .shadow(elevation = 10.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}


@Preview
@Composable
fun PrevMainScreen() {
    MainScreen()
}