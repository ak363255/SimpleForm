package com.example.sqlitetest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.sqlitetest.dbModel.Companion.col_Id
import com.example.sqlitetest.dbModel.Companion.col_email
import com.example.sqlitetest.dbModel.Companion.col_mobileNumber
import com.example.sqlitetest.dbModel.Companion.col_name
import com.example.sqlitetest.dbModel.Companion.databaseNanme
import com.example.sqlitetest.dbModel.Companion.tableName

class dbHelper(var context: Context): SQLiteOpenHelper(context, databaseNanme,null,1) {
    private  val SQL_CREATE_EMPLOYEE_TABLE="CREATE TABLE $tableName ("+
            "$col_Id INTEGER PRIMARY KEY AUTOINCREMENT , "+
            "$col_name TEXT,  "+
            "$col_email TEXT, "+
            "$col_mobileNumber LONG )"
    private val SQL_DROP_TABLE:String="DROP TABLE IF EXISTS $tableName"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_EMPLOYEE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DROP_TABLE)
    }
}