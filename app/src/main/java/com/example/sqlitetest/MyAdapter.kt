package com.example.sqlitetest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitetest.dbModel.Companion.col_Id
import com.example.sqlitetest.dbModel.Companion.tableName

class MyAdapter(var mdata:MutableList<EmpData>) :RecyclerView.Adapter<MyAdapter.myViewHolder>(){
       lateinit var context:Context
    class myViewHolder(view: View):RecyclerView.ViewHolder(view){
        var nameField: TextView =view.findViewById(R.id.Name)
        var emailField:TextView=view.findViewById(R.id.Email)
        var mobNoField:TextView=view.findViewById(R.id.mobile)
        var delete:ImageView=view.findViewById(R.id.delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        context=parent.context
        var view=LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.nameField.setText(mdata[position].name)
        holder.emailField.setText(mdata[position].email)
        holder.mobNoField.setText(mdata[position].mob)
        holder.delete.setOnClickListener(){
            var db=dbHelper(context)
            var database=db.readableDatabase
            database.delete(tableName,"$col_Id=?",arrayOf(mdata[position].id.toString()))
            mdata.removeAt(position)
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener(){
                var intent: Intent =Intent(context,UpdateData::class.java)
                intent.putExtra("Id",mdata[position].id)
                 context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return mdata.size
    }
}