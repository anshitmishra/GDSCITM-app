package com.example.gdscitm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Login::class], version = 1)
abstract class loginDatabase:RoomDatabase() {
    abstract fun loginDao():loginDAO

    companion object {
        @Volatile
        private var INSTANCE : loginDatabase? = null


        fun getDatabase(context: Context): loginDatabase{
            val  tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    loginDatabase::class.java,
                    "loginDB"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }

}