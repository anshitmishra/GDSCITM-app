package com.example.gdscitm.network.apis

import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelist
import com.example.gdscitm.network.model.attendanceSaveList.attendancesavelistsend
import com.example.gdscitm.network.model.attendancelist.attendancelistsenddate
import com.example.gdscitm.network.model.attendancelist.listdata
import com.example.gdscitm.network.model.login.login
import com.example.gdscitm.network.model.login.loginSend
import com.example.gdscitm.network.model.schedule.dayData
import com.example.gdscitm.network.model.schedule.scheduleDataSend
import com.example.gdscitm.network.model.tempsave.tempsaverecive
import com.example.gdscitm.network.model.tempsave.tempsavesend
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
    @POST("attendancelist")
    fun attendancelist(@Body data: attendancelistsenddate):Call<listdata>
    @POST("studentlist")
    fun studentsavelist(@Body data: attendancesavelistsend):Call<attendancesavelist>
    @POST("singleTempInsert")
    fun tempsave(@Body data: tempsavesend):Call<tempsaverecive>





}