package com.example.gdscitm.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
data class Login(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val loginKey: String,
    val time: String,
    val userId: String,
    val userProfile: String,
    val userType: String,
    val username: String
)
