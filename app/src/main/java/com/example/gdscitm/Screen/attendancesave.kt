package com.example.gdscitm.Screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.gdscitm.R
import com.example.gdscitm.database.loginDatabase
import com.example.gdscitm.datastore.StoreUserEmail
import com.example.gdscitm.datastore.userType
import com.example.gdscitm.network.apis.ApiUtilites
import com.example.gdscitm.network.apis.ApisInterface
import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelist
import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelistItem
import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelistsend
import com.example.gdscitm.network.model.tempsave.tempsaverecive
import com.example.gdscitm.network.model.tempsave.tempsavesend
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun saveAttendance(navController : NavHostController, type:String, id:String) {

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            Text(text = "save button")
        },
        content = {
            mainAttendanceList(type,id)
        }
    )

}

@Composable
fun attendanceList(data: attendancesavelistItem,type: String,tempids:String,tids:String) {
    val context = LocalContext.current

    var profile by remember {
        mutableStateOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCbU49DD_iYcjSUEXG-Oy7POjJzaMn1GYEZg&usqp=CAU")
    }
    var check by remember {
        mutableStateOf(false)
    }
    val userApi = ApiUtilites.getInstance().create(ApisInterface::class.java)


    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = data.userProfile,
            contentDescription = null,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(
                    RoundedCornerShape(100.dp)
                )
                .shadow(elevation = 10.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.padding(7.dp,0.dp))
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Text(text = "${data.username}", fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(
                Font(R.font.ubuntu_bold)
            ), color = Color(0xFF676767)
            )
            Text(text = "${data.roll_no}", fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily(
                Font(R.font.ubuntu_bold)
            ), color = Color(0xFF808080))
        }
        Column(modifier = Modifier.fillMaxWidth(0.2f)) {
            Checkbox(checked = check, onCheckedChange = {
                check = !check
                if(check){
                    val datasend = tempsavesend(
                        classId = type,
                        period = "1",
                        studentid = data.roll_no,
                        tempuid = tempids.toString(),
                        tid = tids

                    )
                    val result = userApi.tempsave(datasend)



                }
            } )
        }
    }
}



@Composable
fun mainAttendanceList( type:String, id:String) {
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
    val userApi = ApiUtilites.getInstance().create(ApisInterface::class.java)

    val data = attendancesavelistsend(
        loginKey = savedEmail.value.toString(),
        classId = type.toString()
    )

    var tempid by remember {
        mutableStateOf("")
    }

    var attendancelist by remember {
        mutableStateOf(attendancesavelist())
    }
    val result = userApi.studentsavelist(data)
    result.enqueue(object : Callback<attendancesavelist> {
        override fun onResponse(call: Call<attendancesavelist>, response: Response<attendancesavelist>) {
            Log.d("casdasdasdas","${response.body()}")
            attendancelist = response.body()!!
        }
        override fun onFailure(call: Call<attendancesavelist>, t: Throwable) {
            Log.d("asdasdasdasdasdas","${t.message}")
        }
    })

    Column {

        Text(text = "${id} ${type}")
        LazyColumn() {
            items(attendancelist) { data ->
                attendanceList(data,type,tempid,savedEmail.value.toString())
            }
        }
    }
}




@Preview
@Composable
fun PrevAttendance() {
    saveAttendance(navController = rememberNavController(), type = "c", id = "213213" )
}