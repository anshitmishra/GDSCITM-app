package com.example.gdscitm.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface loginDAO {
    @Insert(onConflict = IGNORE)
    suspend fun loginInsert(login: Login)
    @Delete
    suspend fun deleteLogins(login: Login)

    @Query("SELECT * FROM login WHERE `id` = :uid")
    suspend fun selectLogin(uid:Int): Login

    @Query("DELETE FROM login")
    suspend fun deleteLogin()
}