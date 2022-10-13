package com.example.gdscitm.Screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.gdscitm.R
import com.example.gdscitm.navigation.Navigation
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import kotlin.math.log

@Composable
fun AttendanceMain(navController : NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            TopBar()
        }, bottomBar = {
            BottomAppBar(backgroundColor = Color.White, elevation = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .zIndex(1F) ) {
                BottomNavigationItem(selected = false, onClick = { navController.navigate(Navigation.Home.route) }, icon = ({ Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home"
                )}), label = ({ Text(text = "home", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
                BottomNavigationItem(selected = true, onClick = { navController.navigate(Navigation.Attendance.route) }, icon = ({ Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "attendance",
                )}), label = ({ Text(text = "attendance", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
                BottomNavigationItem(selected = false, onClick = { /*TODO*/ }, icon = ({ Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "setting"
                )}), label = ({ Text(text = "setting", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
            }
        }, content = {
            MainAttendanceView()
            }
        )
    }
}

@Composable
fun MainAttendanceView() {
 Column(modifier = Modifier
     .fillMaxWidth()
     .padding(10.dp)) {
     Row(modifier = Modifier
         .fillMaxWidth()
         .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
         Text(text = "Today", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily(Font(
             R.font.ubuntu_bold))
         )
         Text(text = "select date", modifier = Modifier.fillMaxWidth(), color = Color(0xFF242526), fontSize = 16.sp, fontWeight = FontWeight.Normal, fontFamily = FontFamily(Font(
                 R.font.ubuntu_bold)), textAlign = TextAlign.Right
         )
     }
//LazyColumn(){
//    items(10){
//        AttendanceCard()
//    }
//}
     AppContent()

 }
}


@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppContent() {

    var selectImages by remember { mutableStateOf(listOf<Uri>()) }


    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            selectImages = it
        }


    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { galleryLauncher.launch("image/*") },
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
        ) {
            Text(text = "Pick Image From Gallery")
        }
        selectImages.forEach{
            uri -> Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                        .size(100.dp)
                        .clickable {

                        }
                )
        }
//        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
//            items(selectImages) { uri ->
//                Image(
//                    painter = rememberAsyncImagePainter(uri),
//                    contentScale = ContentScale.FillWidth,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(16.dp, 8.dp)
//                        .size(100.dp)
//                        .clickable {
//
//                        }
//                )
//            }
//        }

    }

}

@Composable
fun AttendanceCard() {
    val archive  = SwipeAction(
        onSwipe = {
            Log.d("MainActivity","archive")
        },
        icon = {
            Icon(painter = painterResource(id = R.drawable.ic_visibility_on), contentDescription = "image" )
        },
        background = Color.Green,
    )

    val emails  = SwipeAction(
        onSwipe = {
            Log.d("MainActivity","Email")
        },
        icon = {
            Icon(painter = painterResource(id = R.drawable.ic_visibility_on), contentDescription = "image" )
        },
        background = Color.Red,
    )
    SwipeableActionsBox(startActions = listOf(archive), endActions = listOf(emails)) {
        Row(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(10.dp, 15.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.test_user), contentDescription =null ,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(shape = RoundedCornerShape(100)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
                Text(text = "Class of cse room no 140",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold)), lineHeight = 20.sp)
                Text(text = "9:00-10:00",
                    color = Color(0xFF242526),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold)),lineHeight = 20.sp)
            }


        }
    }

}


@Preview
@Composable
fun PrevAttendanceMain() {
    AttendanceMain(rememberNavController())
}