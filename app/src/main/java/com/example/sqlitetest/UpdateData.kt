package com.example.sqlitetest

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sqlitetest.dbModel.Companion.col_Id
import com.example.sqlitetest.dbModel.Companion.col_email
import com.example.sqlitetest.dbModel.Companion.col_mobileNumber
import com.example.sqlitetest.dbModel.Companion.col_name
import com.example.sqlitetest.dbModel.Companion.tableName

class UpdateData : AppCompatActivity() {
    lateinit var namee:EditText
    lateinit var email:EditText
    lateinit var mobile:EditText
    lateinit var update:Button
     var id:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        namee=findViewById(R.id.Name)
        email=findViewById(R.id.Email)
        mobile=findViewById(R.id.mobile)
        update=findViewById(R.id.update)
        id=intent.getIntExtra("Id",-1)
        if(id==-1){
            Toast.makeText(this,"False id passed "+id,Toast.LENGTH_LONG).show()
        }
        else{
            var cursor=readData(id)
            if(cursor.count==0){
                Toast.makeText(this,"No data Found",Toast.LENGTH_LONG).show()
            }
            else{
                var nameIndex=cursor.getColumnIndex(col_name)
                var emailInedex=cursor.getColumnIndex(col_email)
                var mobilIndex=cursor.getColumnIndex(col_mobileNumber)
                while(cursor.moveToNext()){
                    namee.setText(cursor.getString(nameIndex))
                    email.setText(cursor.getString(emailInedex))
                    mobile.setText(cursor.getLong(mobilIndex).toString())
                }

                update.setOnClickListener(){
                    var nameString=namee.text.toString()
                    var emailString=email.text.toString()
                    var mobileString:Long= mobile.text.toString().toLong()
                    updateDatabase(nameString,emailString,mobileString)
                }
            }
        }
    }

    fun validateInputs(name: String, emailString: String, mobileText: String): Boolean {
        if (name.length == 0) {
            namee.error = "Please fill the details"
            return false
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                email.error = "Please enter corrent email address"
                return false
            } else {
                if (mobileText.length != 10) {
                    mobile.error = "Please enter correct Mobile No"
                    return false
                }
            }
        }
        return true
    }
    fun readData(id:Int): Cursor {
        var database=dbHelper(this).readableDatabase
        var projection= arrayOf(col_name, col_email, col_mobileNumber)
        var cursor=database.query(tableName,projection,"$col_Id=?",arrayOf(id.toString()),null,null,null)
        return cursor
    }

    fun updateDatabase(name:String,email:String,mobile:Long){
        if(validateInputs(name,email,mobile.toString())){
            var values = ContentValues().apply {
                put(col_name, name)
                put(col_email, email)
                put(col_mobileNumber, mobile)
            }
            var db=dbHelper(this).writableDatabase
            var up = db.update(tableName, values, "$col_Id=$id", null)
            Toast.makeText(this, "$up affected", Toast.LENGTH_LONG).show()
        }
    }
}