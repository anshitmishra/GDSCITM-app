package com.example.gdscitm.Screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.gdscitm.R
import com.example.gdscitm.datastore.StoreUserEmail
import com.example.gdscitm.datastore.userType
import com.example.gdscitm.navigation.Navigation
import com.example.gdscitm.network.apis.ApiUtilites
import com.example.gdscitm.network.apis.ApisInterface
import com.example.gdscitm.network.model.attendancelist.attendancelistsenddate
import com.example.gdscitm.network.model.attendancelist.listdata
import com.example.gdscitm.network.model.attendancelist.listdataItem
import com.example.gdscitm.network.model.schedule.dayData
import com.example.gdscitm.network.model.schedule.scheduleDataSend
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                BottomNavigationItem(selected = false, onClick = {  }, icon = ({ Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "setting"
                )}), label = ({ Text(text = "setting", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
            }
        }, content = {
            MainAttendanceView(navController)
            }
        )
    }
}

@Composable
fun MainAttendanceView(navController: NavHostController) {
    // context
    val context = LocalContext.current
    // datastore Email
    val dataStore = StoreUserEmail(context)
    // get saved email
    val savedEmail = dataStore.getEmail.collectAsState(initial = "")

    // datastore Email
    val userTypeStorage = userType(context)
    // get saved email
    val saveType = userTypeStorage.getType.collectAsState(initial = "")
    //api init
    val userApi = ApiUtilites.getInstance().create(ApisInterface::class.java)
    //sending data from local storage
    var activeDate by remember {
        mutableStateOf("0")
    }
    val data = scheduleDataSend(
        loginKey = savedEmail.value.toString(),
        Date = activeDate.toString()
    )


    var dayD by remember {
        mutableStateOf(dayData())
    }
    val result = userApi.dayData(data)
    result.enqueue(object : Callback<dayData> {
        override fun onResponse(call: Call<dayData>, response: Response<dayData>) {
            Log.d("dasdadsd","${response.body()}")
            dayD = response.body()!!
        }
        override fun onFailure(call: Call<dayData>, t: Throwable) {
            Log.d("dasdadsd","${t.message}")
        }
    })

    var show by remember {
        mutableStateOf(false)
    }

    var attendancelist by remember {
        mutableStateOf(listdata())
    }
    val data2 = attendancelistsenddate(
        loginKey = savedEmail.value.toString(),
        Date = activeDate.toString()
    )
    val result2 = userApi.attendancelist(data2)
    result2.enqueue(object : Callback<listdata>{
        override fun onResponse(call: Call<listdata>, response: Response<listdata>) {
            attendancelist = response.body()!!
        }

        override fun onFailure(call: Call<listdata>, t: Throwable) {
            Log.d("dasdadsd","${t.message}")
        }

    })



 Column(modifier = Modifier
     .fillMaxWidth()
     .padding(10.dp)) {
     Row(modifier = Modifier
         .fillMaxWidth()
         .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
         Text(text = "Today", modifier = Modifier.clickable { activeDate = "0" },fontSize = 18.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily(Font(
             R.font.ubuntu_bold))
         )
         Text(text = "select date", modifier = Modifier
             .fillMaxWidth()
             .clickable { show = !show }, color = Color(0xFF242526), fontSize = 16.sp, fontWeight = FontWeight.Normal, fontFamily = FontFamily(Font(
                 R.font.ubuntu_bold)), textAlign = TextAlign.Right
         )
     }
     if(show) {
         Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .horizontalScroll(rememberScrollState(10))
         ) {
             dayD.forEach { data ->
                 var bgColor: Long = 0xFFF6F6F6
                 if (data.today) {
                     bgColor = 0xFFF1f1f1
                 }
                 Box(
                     modifier = Modifier
                         .padding(10.dp, 15.dp)
                         .clip(shape = RoundedCornerShape(30))
                         .background(
                             Color(bgColor)
                         ),

                     ) {
                     Column(modifier = Modifier
                         .padding(10.dp)
                         .clickable { activeDate = data.date.toString() },
                         horizontalAlignment = Alignment.CenterHorizontally
                     ) {
                         if (data.today) {
                             Box(
                                 modifier = Modifier
                                     .padding(10.dp, 5.dp)
                                     .clip(shape = RoundedCornerShape(30))
                                     .background(
                                         Color(0xFF000000)
                                     )
                                     .width(20.dp)
                                     .height(5.dp)
                             )
                         }
                         Text(
                             text = data.day.toString(), color = Color(0xFFb7b7b7),
                             modifier = Modifier.fillMaxWidth(),
                             textAlign = TextAlign.Center,
                             fontSize = 15.sp,
                             fontWeight = FontWeight.Normal,
                             fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                         )
                         Text(
                             text = data.date,
                             modifier = Modifier.fillMaxWidth(),
                             textAlign = TextAlign.Right,
                             fontSize = 25.sp,
                             fontWeight = FontWeight.Bold,
                             color = Color(0xFF797979),
                             fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                         )

                     }
                 }
             }
         }
     }


LazyColumn(){
    items(attendancelist){data->
        AttendanceCard(navController,data)
    }
}
//     AppContent()

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
fun AttendanceCard(navController : NavHostController,data: listdataItem) {
    val list  = SwipeAction(
        onSwipe = {
            navController.navigate("Attendancelistscreen/${data.classId}/l")
        },
        icon = {
            Icon(imageVector = Icons.Default.List, contentDescription = "list icon",modifier = Modifier
                .clip(shape = RoundedCornerShape(100))
                .padding(10.dp)
                .background(Color.White) )
        },
        background = Color(0xFF97c1a9),
    )

    val camera  = SwipeAction(
        onSwipe = {
            navController.navigate("Attendancelistscreen/${data.classId}/c")
        },
        icon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "camera icon",modifier = Modifier
                .clip(shape = RoundedCornerShape(100))
                .padding(10.dp)
                .background(Color.White) )

        },
        background = Color(0xFFfcb9aa),
    )
    SwipeableActionsBox(startActions = listOf(camera), endActions = listOf(list)) {
        Row(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(10.dp, 15.dp), verticalAlignment = Alignment.CenterVertically,) {


            Text(
                text = data.period,
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.ubuntu_bold)), lineHeight = 20.sp
            )
            Text(
                text = " : :",
                color = Color(0xFFb7b7b7),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily(Font(R.font.ubuntu_bold)), lineHeight = 20.sp,
                modifier = Modifier.padding(5.dp,10.dp)
            )
//                Image(
//                    painter = painterResource(id = R.drawable.test_user), contentDescription = null,
//                    modifier = Modifier
//                        .width(50.dp)
//                        .height(50.dp)
//                        .clip(shape = RoundedCornerShape(100)),
//                    contentScale = ContentScale.Crop
//                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "class ${data.className} room no ${data.classRoomNo}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.ubuntu_bold)), lineHeight = 20.sp
                    )
                    Text(
                        text = data.classTime,
                        color = Color(0xFF242526),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.ubuntu_bold)), lineHeight = 20.sp
                    )
                }
            }
    }

}


@Preview
@Composable
fun PrevAttendanceMain() {
    AttendanceMain(rememberNavController())
}