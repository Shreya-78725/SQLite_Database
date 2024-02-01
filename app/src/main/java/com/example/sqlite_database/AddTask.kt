package com.example.sqlite_database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sqlite_database.Database.DatabaseHelper
import com.example.sqlite_database.Model.TaskListModel

class AddTask : AppCompatActivity() {
    lateinit var et_Name: EditText
    lateinit var et_details: EditText
    lateinit var save: Button
    lateinit var delete: Button
    var dbHandler: DatabaseHelper? = null
    var isEdit: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        save = findViewById(R.id.btn_Save)
        delete = findViewById(R.id.btn_Delete)
        et_Name = findViewById(R.id.edt_EnterName)
        et_details = findViewById(R.id.edt_EnterDetails)
        dbHandler = DatabaseHelper(this)

        if (intent != null && intent.getStringExtra("Mode") == "E") {
            /* update data */
            isEdit = true
            save.text = "Update Data"
            delete.visibility = View.VISIBLE
            val tasks: TaskListModel = dbHandler!!.getTask(intent.getIntExtra(Constants.tableColId, 0))
            et_Name.setText(tasks.name)
            et_details.setText(tasks.details)

        } else {
            /*insert new data and don't want delete btn */

            isEdit = false
            save.text = "Save Data"
            delete.visibility = View.GONE
        }
        save.setOnClickListener {
            var success: Boolean = false
            val task: TaskListModel = TaskListModel()
            if (isEdit) {
                /* update data */
                task.id = intent.getIntExtra("id", 0)
                task.name=et_Name.text.toString()
                task.details=et_details.text.toString()
                success=dbHandler?.updateTask(task) as Boolean

            } else {
                /* insert new data*/
                task.name = et_Name.text.toString()
                task.details = et_details.text.toString()
                success = dbHandler?.addTask(task) as Boolean
            }
            if (success) {
                val i = Intent(applicationContext, Home::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(applicationContext, "wrong", Toast.LENGTH_SHORT).show()
            }
        }
delete.setOnClickListener{
    val dialog=AlertDialog.Builder(this).setTitle("info").setMessage("delete if want").setPositiveButton("yes",
        {dialog, i -> val success=dbHandler?.deleteTask(intent.getIntExtra("id",0)) as Boolean
            if(success){
                finish()
                dialog.dismiss()
            }
        }) .setNegativeButton("No",{dialog, i-> dialog.dismiss()})
    dialog.show()
}

    }
}