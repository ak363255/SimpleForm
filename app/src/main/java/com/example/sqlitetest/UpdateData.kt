package com.example.sqlitetest

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sqlitetest.Room.EmpDataEntity
import com.example.sqlitetest.Room.MyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateData : AppCompatActivity() {
    lateinit var namee: EditText
    lateinit var email: EditText
    lateinit var mobile: EditText
    lateinit var update: Button
    var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        namee = findViewById(R.id.Name)
        email = findViewById(R.id.Email)
        mobile = findViewById(R.id.mobile)
        update = findViewById(R.id.update)
        id = intent.getIntExtra("Id", -1)
        if (id == -1) {
            Toast.makeText(this, "False id passed " + id, Toast.LENGTH_LONG).show()
        } else {
            var DbData: EmpDataEntity? = null
            GlobalScope.launch {
                DbData = MyDatabase.getDatabase(this@UpdateData).getUserDao().getsingleRow(id)
                if (DbData == null) {
                    Log.d("Record", "No record found")
                } else {


                    withContext(Dispatchers.Main) {
                        namee.setText(DbData!!.col_Name)
                        email.setText(DbData!!.col_Email)
                        mobile.setText(DbData!!.col_Phoone.toString())
                        update.setOnClickListener()
                        {
                            var nameString = namee.text.toString()
                            var emailString = email.text.toString()
                            var mobileString: Long = mobile.text.toString().toLong()
                            updateDatabase(nameString, emailString, mobileString, DbData?.col_Id!!)
                            update.setBackgroundColor(
                                ContextCompat.getColor(
                                    this@UpdateData,
                                    R.color.grey
                                )
                            )
                            update.setTextColor(
                                ContextCompat.getColor(
                                    this@UpdateData,
                                    android.R.color.black
                                )
                            )
                            update.isEnabled = false
                        }
                    }
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

    fun updateDatabase(name: String, email: String, mobile: Long, id: Int) {
        if (validateInputs(name, email, mobile.toString())) {
            GlobalScope.launch {
                val Db = MyDatabase.getDatabase(this@UpdateData).getUserDao()
                var empData = EmpDataEntity(name, email, mobile)
                empData.col_Id = id
                Db.update(empData)
                Log.d("updated", "success")

            }
        }
    }
}