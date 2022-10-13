package com.example.gdscitm.network.apis

import com.example.gdscitm.network.model.login.login
import com.example.gdscitm.network.model.login.loginSend
import com.example.gdscitm.network.model.schedule.dayData
import com.example.gdscitm.network.model.schedule.scheduleDataSend
import com.example.gdscitm.network.model.timetable.timetableData
import com.example.gdscitm.network.model.timetable.timetableDataSend
import com.example.gdscitm.network.model.user.user
import com.example.gdscitm.network.model.user.userDetailsSend
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApisInterface {
    @POST("login")
    fun TeacherLogin(@Body data: loginSend):Call<login>
    @POST("userDetails")
    fun userDetails(@Body data: userDetailsSend):Call<user>
    @POST("schedule")
    fun dayData(@Body data: scheduleDataSend):Call<dayData>
    @POST("timetable")
    fun timetable(@Body data: timetableDataSend):Call<timetableData>
    @POST("studenttimetable")
    fun studenttimetable(@Body data: timetableDataSend):Call<timetableData>

}