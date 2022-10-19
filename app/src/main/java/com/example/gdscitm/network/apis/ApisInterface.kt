package com.example.gdscitm.network.apis

import com.example.gdscitm.network.model.attendancelist.attendancelistsenddate
import com.example.gdscitm.network.model.attendancelist.listdata
import com.example.gdscitm.network.model.list.attendanceListDataSave
import com.example.gdscitm.network.model.list.attendancelistsenddata
import com.example.gdscitm.network.model.login.login
import com.example.gdscitm.network.model.login.loginSend
import com.example.gdscitm.network.model.schedule.dayData
import com.example.gdscitm.network.model.schedule.scheduleDataSend
import com.example.gdscitm.network.model.timetable.timetableData
import com.example.gdscitm.network.model.timetable.timetableDataSend
import com.example.gdscitm.network.model.user.user
import com.example.gdscitm.network.model.user.userDetailsSend
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


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
    @POST("attendancelist")
    fun attendancelist(@Body data: attendancelistsenddate):Call<listdata>
//    @POST("studentlist")
//    fun studentsavelist(@Body data: attendancesavelistsend):Call<attendanceListDataSave>
//    @POST("singleTempInsert")
//    fun tempsave(@Body data: tempsavesend):Call<tempsaverecive>
//    @POST("studentlistimagegen")
    fun studentlistimagegen(@Body data: attendancelistsenddata):Call<attendanceListDataSave>
    @POST("studentlistgen")
    fun studentlistgen(@Body data: attendancelistsenddata):Call<attendanceListDataSave>

    @Multipart
    @POST("present/image")
    fun updateProfile(
        @Part("noimage") id: Int?,
        @Part("classname") classname: RequestBody?,
        @Part("period") period: RequestBody?,
        @Part("tid") tid: RequestBody?,
        @Part("tempid") tempid: RequestBody?,
        @Part image: MultipartBody.Part,
    ): Call<String>




}