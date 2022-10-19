package com.example.gdscitm.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.gdscitm.R
import com.example.gdscitm.datastore.StoreUserEmail
import com.example.gdscitm.datastore.userType
import com.example.gdscitm.navigation.Navigation
import com.example.gdscitm.network.apis.ApiUtilites
import com.example.gdscitm.network.apis.ApisInterface
import com.example.gdscitm.network.model.login.login
import com.example.gdscitm.network.model.login.loginSend
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Login(navController : NavHostController) {
Surface(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier.padding(10.dp)) {
        Image(painter = painterResource(id = R.drawable.login_logo), contentDescription = "login logo", modifier = Modifier
            .fillMaxWidth()
            .size(250.dp))
        Column(modifier = Modifier.padding(10.dp)) {
            var email by remember {
                mutableStateOf("")
            }
            var loginText  by remember {
                mutableStateOf("Login account")
            }
            var errorMessage = ""
            val context = LocalContext.current
            var password by remember {
                mutableStateOf("")
            }
            val dataStore = StoreUserEmail(context)
            val userType = userType(context)

            // scope
            val scope = rememberCoroutineScope()
            var passwordVisibility: Boolean by remember { mutableStateOf(false) }
            Text(text = "Teacher Login", fontWeight = FontWeight.SemiBold, fontFamily = FontFamily(Font(R.font.ubuntu_bold)), fontSize = 30.sp, modifier = Modifier.padding(horizontal = 5.dp,vertical = 10.dp))
            OutlinedTextField(value = email, onValueChange = {email = it}, maxLines = 1,
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "Email")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email")}
                )

            OutlinedTextField(value = password, onValueChange = {password = it}, maxLines = 1,
                modifier = Modifier
                    .padding(5.dp, 10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "Password")},
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "password")},
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        val passwordPainter = when(passwordVisibility) {
                            false ->  R.drawable.ic_visibility_on
                            true ->  R.drawable.ic_visibility_off
                            else ->  R.drawable.ic_visibility_on
                        }
                        Icon(
                            painter = painterResource(id = passwordPainter),
                            contentDescription = "eye password"
                        )
                    }
                }
            )

            Button(onClick = {
                loginText = "logging in..."
                             val userApi=ApiUtilites.getInstance().create(ApisInterface::class.java)

                                val data = loginSend(
                                    email = email,
                                    password = password,
                                    userType = "T"
                                )
                                val result = userApi.TeacherLogin(data)
                                result.enqueue(object : Callback<login>{
                                    override fun onResponse(
                                        call: Call<login>,
                                        response: Response<login>
                                    ) {
                                        Log.d("asdksndalsdlamsd",response.body().toString())
                                        if (response.body() != null) {
                                                if(response.body()!!.message == "password not match"){
                                                    errorMessage = "password not match"
                                                    loginText = "Login account"
                                                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                                }else{
                                                    Log.d("data", response.body()!!.data[0].email)
                                                    Toast.makeText(context, "login successfully", Toast.LENGTH_LONG).show()
                                                    navController.navigate(Navigation.Home.route)
                                                    //saving login token in local
                                                    scope.launch {
                                                        dataStore.saveEmail(response.body()!!.data[0].loginKey)
                                                        userType.saveType("T")
                                                    }
                                                    loginText = "Login account"
                                                }
                                            }else{
                                            loginText = "Login account"
                                        }
                                    }
                                    override fun onFailure(call: Call<login>, t: Throwable) {
                                        loginText = "Login account"
                                        Log.d("dassads","${t.message}")
                                        Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
                                    }

                                })
                }, modifier = Modifier.padding(5.dp,10.dp)) {
                Text(text = loginText,fontSize = 16.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.ubuntu_bold)))
            }

        }
    }
}

}

@Preview
@Composable
fun PrevLogin() {
    Login(navController = rememberNavController())
}

