package com.example.sqlitetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitetest.Room.EmpDataEntity
import com.example.sqlitetest.Room.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var data: MutableList<EmpData>
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var myadapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        recyclerView = findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(this)
        data = mutableListOf()
        recyclerView.layoutManager = linearLayoutManager
        myadapter = MyAdapter(data)
        recyclerView.adapter = myadapter
        loadData()
    }

    private fun loadData() {
        val Db = MyDatabase.getDatabase(this).getUserDao()
        var Dbdata = mutableListOf<EmpDataEntity>()
        GlobalScope.launch {
            Dbdata = Db.query()
            if (Dbdata.size == 0) {
                Log.d("size", "Size 0")
            } else {
                for (item in Dbdata) {
                    data.add(
                        EmpData(
                            item.col_Name,
                            item.col_Email,
                            item.col_Phoone.toString(),
                            item.col_Id
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    myadapter.notifyDataSetChanged()
                }

            }
        }

    }

    override fun onRestart() {
        super.onRestart()
        data.clear()
        loadData()
    }
}