package com.example.gdscitm.Screen

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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
                  MainContent()
              }
          )
        }
}

@Composable
fun MainContent() {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        ) {
        //calender horizontal view
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())) {
                for (i in 1..30) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp, 15.dp)
                            .clip(shape = RoundedCornerShape(30))
                            .background(
                                Color(0xFFF6F6F6)
                            )
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "wed", color = Color(0xFFb7b7b7),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                            )
                            Text(
                                text = "$i",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                            )
                        }
                    }
                }
            }


            //time line vertical view
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {

                for (i in 9..17) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "$i : 00",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.ubuntu_bold)))
                        Spacer(modifier = Modifier.padding(horizontal = 20.dp))
                        Box(modifier = Modifier
                            .clip(shape = RoundedCornerShape(20))
                            .background(Color(0xFF242424))
                            .padding(10.dp)){
                            Text(text = buildAnnotatedString {
                                append("Rena Ma'am Class\n")
                                withStyle(style = SpanStyle(color = Color(0xFF606060), fontSize = 15.sp,)) {
                                    append("9:00-10:00")
                                }
                            },
                                lineHeight = 25.sp,
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily(Font(R.font.ubuntu_bold)))
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
            }
        }
    }
}
@Composable
fun BottomBar() {
    BottomAppBar(backgroundColor = Color.White, elevation = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .zIndex(1F) ) {
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