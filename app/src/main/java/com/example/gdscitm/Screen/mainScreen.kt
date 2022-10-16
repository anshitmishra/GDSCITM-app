package com.example.gdscitm.Screen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.gdscitm.R
import com.example.gdscitm.datastore.StoreUserEmail
import com.example.gdscitm.datastore.userType
import com.example.gdscitm.navigation.Navigation
import com.example.gdscitm.network.apis.ApiUtilites
import com.example.gdscitm.network.apis.ApisInterface
import com.example.gdscitm.network.model.schedule.dayData
import com.example.gdscitm.network.model.schedule.scheduleDataSend
import com.example.gdscitm.network.model.timetable.timetableData
import com.example.gdscitm.network.model.timetable.timetableDataSend
import com.example.gdscitm.network.model.user.user
import com.example.gdscitm.network.model.user.userDetailsSend
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MainScreen(navController : NavHostController) {
    Surface(modifier = Modifier.fillMaxSize()) {
          Scaffold(
              topBar = {
                       TopBar()
              },
              bottomBar = {
                  BottomBar(navController)
              },
              content = {
                  MainContent()
              }
          )
        }
}


@Composable
fun DetailsListViewTeacher(timetableD: timetableData) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .paddingFromBaseline(20.dp, 40.dp),

        verticalArrangement = Arrangement.Center
    ) {
        items(timetableD) { data ->
            val backColor:Long = data.background.toLong()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${data.classTime}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF797979),
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold))

                )
                Spacer(modifier = Modifier.padding(horizontal = (20+(data.period.toInt()*2)).dp))
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20))
                        .background(Color(backColor))
                        .padding(20.dp, 10.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("subject ${data.classSubName}\n")
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 15.sp
                                )
                            ) {
                                append("room ${data.classRoomNo}\t class ${data.className}")
                            }
                        },
                        lineHeight = 25.sp,
                        color = Color(0xFF797979),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                    )
                }
            }
        }
        item{
            Spacer(modifier = Modifier.padding(0.dp,30.dp))
        }
    }
}


@Composable
fun DetailsListViewStudent(timetableD: timetableData) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .paddingFromBaseline(20.dp, 40.dp),

        verticalArrangement = Arrangement.Center
    ) {
        items(timetableD) { data ->
            val backColor:Long = data.background.toLong()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${data.classTime}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF797979),
                    fontFamily = FontFamily(Font(R.font.ubuntu_bold))

                )
                Spacer(modifier = Modifier.padding(horizontal = (20+(data.period.toInt()*2)).dp))
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20))
                        .background(Color(backColor))
                        .padding(20.dp, 10.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("subject ${data.classSubName}\n")
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 15.sp
                                )
                            ) {
                                append("${data.name}\t room ${data.classRoomNo}")
                            }
                        },
                        lineHeight = 25.sp,
                        color = Color(0xFF797979),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                    )
                }
            }
        }
        item{
            Spacer(modifier = Modifier.padding(0.dp,30.dp))
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainContent() {
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
    result.enqueue(object : Callback<dayData>{
        override fun onResponse(call: Call<dayData>, response: Response<dayData>) {
            Log.d("dasdadsd","${response.body()}")
            dayD = response.body()!!
        }
        override fun onFailure(call: Call<dayData>, t: Throwable) {
            Log.d("dasdadsd","${t.message}")
        }
    })
    var timetableD by remember {
        mutableStateOf(timetableData())
    }
    val data2 = timetableDataSend(
        loginKey = savedEmail.value.toString(),
        Date = activeDate.toString()
    )
//    studenttimetable
    val resulttimetable = if(saveType.value.toString() == "T") {
        userApi.timetable(data2)
    }else{
        userApi.studenttimetable(data2)
    }
    resulttimetable.enqueue(object : Callback<timetableData>{
        override fun onResponse(call: Call<timetableData>, response: Response<timetableData>) {
            Log.d("dasdadsd","${response.body()}")
            timetableD = response.body()!!
        }

        override fun onFailure(call: Call<timetableData>, t: Throwable) {
            Log.d("dasdadsd","${t.message}")
        }
    })
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        //calender horizontal view
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(bottom = 20.dp)
        ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState(10))) {
                    dayD.forEach{ data ->
                        var bgColor:Long = 0xFFF6F6F6
                        if(data.today) {
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
                                .clickable { activeDate = data.date.toString() }, horizontalAlignment = Alignment.CenterHorizontally) {
                                if(data.today) {
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



            //time line vertical view
            if(saveType.value.toString() == "T") {
                DetailsListViewTeacher(timetableD)
            }else{
                DetailsListViewStudent(timetableD)
            }
        }
    }
}


@Composable
fun BottomBar(navController : NavHostController) {

    BottomAppBar(backgroundColor = Color.White, elevation = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .zIndex(1F) ) {
        BottomNavigationItem(selected = true, onClick = { navController.navigate(Navigation.Home.route) }, icon = ({ Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "home"
        )}), label = ({ Text(text = "home", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
        BottomNavigationItem(selected = false, onClick = { navController.navigate(Navigation.Attendance.route) }, icon = ({ Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "attendance",
        )}), label = ({ Text(text = "attendance", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
        BottomNavigationItem(selected = false, onClick = {  }, icon = ({ Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "setting"
        )}), label = ({ Text(text = "setting", fontWeight = FontWeight.Bold, fontSize = 16.sp)}))
    }
}


@Composable
fun TopBar() {
    //
        var name by remember {
            mutableStateOf("user")
        }
        var profile by remember {
            mutableStateOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCbU49DD_iYcjSUEXG-Oy7POjJzaMn1GYEZg&usqp=CAU")
        }
        if(name == "user") {
            // context
            val context = LocalContext.current
            // datastore Email
            val dataStore = StoreUserEmail(context)
            // get saved email
            val savedEmail = dataStore.getEmail.collectAsState(initial = "")

            //api init
            val userApi = ApiUtilites.getInstance().create(ApisInterface::class.java)
            //sending data from local storage
            val data = userDetailsSend(
                loginKey = savedEmail.value.toString()
            )
            val result = userApi.userDetails(data)
            result.enqueue(object : Callback<user> {
                override fun onResponse(call: Call<user>, response: Response<user>) {
                    name = response.body()?.data?.get(0)?.username.toString()
                    profile = response.body()?.data?.get(0)?.userProfile.toString()
                    Log.d("dataRead", "${response.body()}")
                }

                override fun onFailure(call: Call<user>, t: Throwable) {
                    Log.d("dataRead", "${t.message}")
                    Toast.makeText(context, "error restart app", Toast.LENGTH_LONG).show()
                }

            })
        }
    TopAppBar(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(), backgroundColor = Color.Transparent, elevation = 0.dp) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.fillMaxWidth(0.8F)) {
                Text(text = "Hii ${name},", fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily =  FontFamily(
                    Font(R.font.ubuntu_medium)
                ))
                Text(text = "Today Schedule", fontWeight = FontWeight.Medium, fontSize = 15.sp,fontFamily =  FontFamily(
                    Font(R.font.ubuntu_medium)
                ))
            }
            Column(modifier = Modifier.fillMaxWidth(0.8F)) {
                AsyncImage(
                    model = profile,
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .shadow(elevation = 10.dp),
                    contentScale = ContentScale.Crop,
                )
//                Image(
//                    painter = painterResource(id = R.drawable.test_user),
//                    contentDescription = "login",
//                    modifier = Modifier
//                        .width(100.dp)
//                        .height(100.dp)
//                        .clip(
//                            RoundedCornerShape(10.dp)
//                        )
//                        .shadow(elevation = 10.dp),
//                    contentScale = ContentScale.Crop,
//                )
            }
        }
    }
}


@Preview
@Composable
fun PrevMainScreen() {
    MainScreen(rememberNavController())
}