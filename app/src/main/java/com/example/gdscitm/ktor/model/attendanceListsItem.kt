package com.example.gdscitm.ktor.model

import kotlinx.serialization.Serializable

@Serializable
data class attendanceListsItem(
    val image: String,
    val name: String,
    val rollno: String,
    val t: Boolean
)