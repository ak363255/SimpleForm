package com.example.sqlitetest.Room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmpDataEntity::class],version=1)
 abstract class MyDatabase:RoomDatabase() {

      abstract fun getUserDao():UserDao

     companion object{
         @Volatile
         private var DatabaseInstance:com.example.sqlitetest.Room.MyDatabase?=null
         fun getDatabase(context:Context):com.example.sqlitetest.Room.MyDatabase{

             if(DatabaseInstance==null){
                  synchronized(this){
                      var instance= Room.databaseBuilder(
                          context.applicationContext,
                          MyDatabase::class.java,
                          "mydatabase"
                      ).build()
                      DatabaseInstance=instance
                  }

             }
             return DatabaseInstance!!

         }


     }





 }