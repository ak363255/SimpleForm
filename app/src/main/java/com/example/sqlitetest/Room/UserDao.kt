package com.example.sqlitetest.Room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(data:EmpDataEntity)
    @Update
    fun update(data:EmpDataEntity):Int
    @Query("Delete from EmpDataEntity where col_Id=:id")
    fun delete(id:Int)
    @Query("Select * from EmpDataEntity")
    fun query():MutableList<EmpDataEntity>
    @Query("Select * from EmpDataEntity where col_Id=:id")
    fun getsingleRow(id:Int):EmpDataEntity
}