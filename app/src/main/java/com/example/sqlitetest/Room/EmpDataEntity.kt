package com.example.sqlitetest.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmpDataEntity(

    val col_Name:String,
    val col_Email:String,
    val col_Phoone:Long
){
    @PrimaryKey(autoGenerate = true)
    var col_Id:Int=0
}