package com.example.sqlitetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitetest.dbModel.Companion.col_Id
import com.example.sqlitetest.dbModel.Companion.col_email
import com.example.sqlitetest.dbModel.Companion.col_mobileNumber
import com.example.sqlitetest.dbModel.Companion.col_name
import com.example.sqlitetest.dbModel.Companion.tableName

class DetailActivity : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    lateinit var data:MutableList<EmpData>
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var myadapter:MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        recyclerView=findViewById(R.id.recyclerView)
        linearLayoutManager= LinearLayoutManager(this)
        data= mutableListOf()
        recyclerView.layoutManager=linearLayoutManager
        myadapter= MyAdapter(data)
        recyclerView.adapter=myadapter
        loadData()

    }

    private fun loadData() {
        var db=dbHelper(this)
        var readAbleDatabase=db.readableDatabase
        var cursor=readAbleDatabase.rawQuery("select * from $tableName",null)
        if(cursor.count==0){
            Toast.makeText(this,"No data found",Toast.LENGTH_LONG).show()
        }
        else{
            var nameColIndex=cursor.getColumnIndex(col_name)
            var emailColIndex=cursor.getColumnIndex(col_email)
            var mobileColIndex=cursor.getColumnIndex(col_mobileNumber)
            var id=cursor.getColumnIndex(col_Id)
            while(cursor.moveToNext()){
                data.add(EmpData(cursor.getString(nameColIndex),cursor.getString(emailColIndex)
                          ,cursor.getLong(mobileColIndex).toString(),cursor.getInt(id)))
            }
            myadapter.notifyDataSetChanged()

        }
    }

    override fun onRestart() {
        super.onRestart()
        data.clear()
        loadData()
    }
}