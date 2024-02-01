package com.example.sqlite_database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_database.Database.DatabaseHelper
import com.example.sqlite_database.Model.TaskListModel

class Home : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var add: Button
    var taskListAdapter: TaskListAdapter? = null
    var dbHandler: DatabaseHelper? = null
    var tasklist: List<TaskListModel> = ArrayList<TaskListModel>()
    var linearLayoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recycler = findViewById(R.id.recyclerView)
        add = findViewById(R.id.btn_Add)
        dbHandler = DatabaseHelper(this)
        fetch()

        add.setOnClickListener{
            val i= Intent(applicationContext,AddTask::class.java)
            startActivity(i)
        }

    }

    private fun fetch() {
        tasklist = dbHandler!!.getAll() //
        taskListAdapter = TaskListAdapter(tasklist, applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()

    }
}