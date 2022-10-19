package com.example.gdscitm.Screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.example.gdscitm.datastore.StoreUserEmail
import com.example.gdscitm.datastore.userType
import com.example.gdscitm.network.apis.ApiUtilites
import com.example.gdscitm.network.apis.ApisInterface
import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelist
import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelistItem
import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelistsend
import com.example.gdscitm.network.model.attendancelist.attendancelistsenddate
import com.example.gdscitm.network.model.list.attendanceListDataSave
import com.example.gdscitm.network.model.list.attendanceListDataSaveItem
import com.example.gdscitm.network.model.list.attendancelistsenddata
import com.example.gdscitm.network.model.tempsave.tempsavesend
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import org.apache.commons.io.FileUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


@Composable
fun saveAttendance(navController : NavHostController, type:String,tempid: String,period:String, id:String) {

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            Text(text = "save button")
        },
        content = {
            mainAttendanceList(type,tempid,period,id)
        }
    )

}

@Composable
fun attendanceList(data: attendanceListDataSaveItem,type: String,tempids:String,tids:String) {
    val context = LocalContext.current

    var profile by remember {
        mutableStateOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCbU49DD_iYcjSUEXG-Oy7POjJzaMn1GYEZg&usqp=CAU")
    }
    var check by remember {
        mutableStateOf(data.t)
    }
    val userApi = ApiUtilites.getInstance().create(ApisInterface::class.java)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = data.image,
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
        Spacer(modifier = Modifier.padding(7.dp, 0.dp))
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Text(
                text = "${data.name}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.ubuntu_bold)
                ),
                color = Color(0xFF676767)
            )
            Text(
                text = "${data.rollno}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(
                    Font(R.font.ubuntu_bold)
                ),
                color = Color(0xFF808080)
            )
        }
        Column(modifier = Modifier.fillMaxWidth(0.2f)) {
            Checkbox(checked = check, onCheckedChange = {
                check = !check
                if (check) {
                    val datasend = tempsavesend(
                        classId = type,
                        period = "1",
                        studentid = data.rollno,
                        tempuid = tempids.toString(),
                        tid = tids.toString()
                        )
                }
            }
            )


        }
    }
}


private fun createFileFromUri(name: String, uri: Uri,context: Context): File? {
    return try {
        val stream = context.contentResolver.openInputStream(uri)
        val file =
            File.createTempFile(
                "${name}_${System.currentTimeMillis()}",
                ".png",
                context.cacheDir
            )
        FileUtils.copyInputStreamToFile(stream, file)  // Use this one import org.apache.commons.io.FileUtils
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun mainAttendanceList( type:String,tempid:String ,period:String, id:String) {
// context
    var temp = "knknslanl"
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
    val userApi2 = ApiUtilites.getInstanceTwo().create(ApisInterface::class.java)

    val data = attendancesavelistsend(
        loginKey = savedEmail.value.toString(),
        classId = type.toString()
    )

    var tempid by remember {
        mutableStateOf("")
    }

    var attendancelist by remember {
        mutableStateOf(attendanceListDataSave())
    }
//    val result2 = userApi2.attendance()
//    result2.enqueue(object : Callback<String>{
//        override fun onResponse(call: Call<String>, response: Response<String>) {
//            Log.d("asdajhsdkasnjdkb",response.toString())
//        }
//
//        override fun onFailure(call: Call<String>, t: Throwable) {
//            Log.d("asdajhsdkasnjdkb",t.toString())
//        }
//
//    })


    val data2 = attendancelistsenddata(
        tempid = "knknslanl",
        classname = id,
        period = period,
        tid = savedEmail.value.toString()
    )
    Log.d("asasdasdasdasda"," ${ data2.toString() } sadasd ${type}")
    if(type == "c"){
        val result = userApi.studentlistimagegen(data2)
        result.enqueue(object : Callback<attendanceListDataSave> {
            override fun onResponse(call: Call<attendanceListDataSave>, response: Response<attendanceListDataSave>) {
                Log.d("casdasdasdas","${response.body()}")
                attendancelist = response.body()!!

            }
            override fun onFailure(call: Call<attendanceListDataSave>, t: Throwable) {
                Log.d("asdasdasdasdasdas","${t.message}")
            }
        })
    }else if(type == "l"){
        val result = userApi.studentlistgen(data2)
        result.enqueue(object : Callback<attendanceListDataSave> {
            override fun onResponse(call: Call<attendanceListDataSave>, response: Response<attendanceListDataSave>) {
                Log.d("casdasdasdas","${response.body()}")
                attendancelist = response.body()!!

            }
            override fun onFailure(call: Call<attendanceListDataSave>, t: Throwable) {
                Log.d("asdasdasdasdasdas","${t.message}")
            }
        })
    }else {
        Log.d("asdasdasdasdasdas","nothing  is coming ")

    }


    Column {

        Text(text = "${id} ${type}")
        LazyColumn() {
            items(attendancelist) { data ->
                attendanceList(data,type,tempid,savedEmail.value.toString())
            }
        }
    }


//    RequestContentPermission()
}
@SuppressLint("SuspiciousIndentation")
@Composable
fun RequestContentPermission() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    var uploadurl by remember {
        mutableStateOf("")
    }
    val userApi2 = ApiUtilites.getInstanceTwo().create(ApisInterface::class.java)

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        if (uri != null) {
            var sada = createFileFromUri("tempimaage",uri,context)
            uploadurl = sada.toString()
            val file = File(uploadurl)
            Log.d("asdhjkasbdk",file.toString())
            val requestFile: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val body: MultipartBody.Part = createFormData("image", file.name, requestFile)
            val fullName: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "anshit")



        }
    }


    Column() {
        Button(onClick = {
            launcher.launch("image/*")
        }) {
            Text(text = "Pick image")
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let {  btm ->
//                GlobalScope.launch {
//                    var aasd = apis().uploadImage("Asd",btm)
//                        Log.d("ASdasdasdas",aasd.toString())
//                }
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(400.dp))
            }
        }

    }
}



@Preview
@Composable
fun PrevAttendance() {
    saveAttendance(navController = rememberNavController(), type = "c", id = "213213" , period = "asdasd", tempid = "Asasds")
}