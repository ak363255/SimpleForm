package com.example.sqlitetest

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.sqlitetest.dbModel.Companion.col_email
import com.example.sqlitetest.dbModel.Companion.col_mobileNumber
import com.example.sqlitetest.dbModel.Companion.col_name
import com.example.sqlitetest.dbModel.Companion.tableName
import com.google.android.material.textfield.TextInputEditText

const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

class MainActivity : AppCompatActivity() {
    lateinit var db: dbHelper
    lateinit var Username: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var mobileNo: TextInputEditText
    lateinit var submit: Button
    lateinit var loadData:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = dbHelper(this)
        Username = findViewById(R.id.Name)
        email = findViewById(R.id.Email)
        mobileNo = findViewById(R.id.mobile)
        submit = findViewById(R.id.submit)
        loadData=findViewById(R.id.loadData)
        submit.setOnClickListener() {
            saveData()
        }
        loadData.setOnClickListener(){
            startDetailActivity()
        }

    }

    private fun saveData() {
        var nameString = Username.text.toString()
        var emailString = email.text.toString()
        var mobileNoText = mobileNo.text.toString()
        var writeDatabase = db.writableDatabase
        if (validateInputs(nameString, emailString, mobileNoText)) {
            val values: ContentValues = ContentValues().apply {
                put(col_name, nameString)
                put(col_email, emailString)
                put(col_mobileNumber, mobileNoText.toLong())
            }
            var id: Long = writeDatabase.insert(tableName, null, values)
            if (id == -1L) Toast.makeText(this, "Fail to Insert data", Toast.LENGTH_LONG).show()
            else {
                Toast.makeText(this, " Insert data successsfully at id " + id, Toast.LENGTH_LONG)
                    .show()
                startDetailActivity()
            }
        }


    }

    private fun startDetailActivity() {
        startActivity(Intent(this, DetailActivity::class.java))
    }

    fun validateInputs(name: String, emailString: String, mobileText: String): Boolean {
        if (name.length == 0) {
            Username.error = "Please fill the details"
            return false
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                email.error = "Please enter corrent email address"
                return false
            } else {
                if (mobileText.length != 10) {
                    mobileNo.error = "Please enter correct Mobile No"
                    return false
                }
            }
        }
        return true
    }

    override fun onRestart() {
        super.onRestart()
        Username.setText("")
        email.setText("")
        mobileNo.setText("")
    }
}