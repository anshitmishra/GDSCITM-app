package com.example.gdscitm.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
            var password by remember {
                mutableStateOf("")
            }
            var passwordVisibility: Boolean by remember { mutableStateOf(false) }
            Text(text = "Login", fontWeight = FontWeight.SemiBold, fontFamily = FontFamily(Font(R.font.ubuntu_bold)), fontSize = 30.sp, modifier = Modifier.padding(horizontal = 5.dp,vertical = 10.dp))
            OutlinedTextField(value = email, onValueChange = {email = it}, maxLines = 1,
                modifier = Modifier.padding(5.dp,10.dp).fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "Email")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                label = { Text(text = "Email")},
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email")}
                )

            OutlinedTextField(value = password, onValueChange = {password = it}, maxLines = 1,
                modifier = Modifier.padding(5.dp,10.dp).fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "Password")},
//                label = { Text(text = "Password")},
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "password")},
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        var passwordPainter = when(passwordVisibility) {
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
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(5.dp,10.dp)) {
                Text(text = "Login account")
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