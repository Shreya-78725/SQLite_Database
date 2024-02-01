package com.example.sqlite_database.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite_database.Model.TaskListModel

class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, db_name,null,db_version){

    companion object{
       private val db_name="task"
        private  val db_version=3
        private val table_name="taskList"
        private val ID="id"
        private val taskName="taskname"
        private val task_details="taskDetails"
    }
 /** here we create table and database both **/

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table="CREATE TABLE $table_name(" +
                "$ID INTEGER PRIMARY KEY," +
                taskName + " TEXT,"+
                task_details +" TEXT)"
        db?.execSQL(create_table)
    }
 /** to drop table **/

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table="DROP TABLE IF EXISTS $table_name"
        db?.execSQL(drop_table)
        onCreate(db)
    }

    /** Select query */

    @SuppressLint("Range")
    fun getAll() : List<TaskListModel>{

        val taskList=ArrayList<TaskListModel>()
        val db = writableDatabase      /** Create and/or open a database that will be used for reading and writing **/
        val selectQuery = "SELECT *FROM $table_name"
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do {
                    val task=TaskListModel()
                    task.id= Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    task.name= cursor.getString(cursor.getColumnIndex(taskName))
                    task.details=cursor.getString(cursor.getColumnIndex(task_details))
                    taskList.add(task)
                }
                    while (cursor.moveToNext())
            }
        }
        cursor.close()
        return taskList
    }

    /** Insert query **/

    fun addTask(tasks : TaskListModel) : Boolean{
       val db = this.writableDatabase
        val values = ContentValues()
        values.put(taskName,tasks.name)
        values.put(task_details,tasks.details)
        val _success= db.insert(table_name,null,values)
        db.close()
        return (Integer.parseInt("$_success")!=-1)
    }

    /** select data of particular id **/

    @SuppressLint("Range")
    fun getTask(id:Int) : TaskListModel{
        val task=TaskListModel()
        val db = writableDatabase
        val selectQuery = "SELECT *FROM $table_name where $ID= $id"
        val cursor = db.rawQuery(selectQuery,null)
        cursor?.moveToFirst()
        task.id= Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        task.name= cursor.getString(cursor.getColumnIndex(taskName))
        task.details=cursor.getString(cursor.getColumnIndex(task_details))
        cursor.close()
        return task
    }

    /** to delete particular data from table **/

    fun deleteTask(id : Int) : Boolean{
        val db=this.writableDatabase
        val success= db.delete(table_name, "$ID=${id}", arrayOf()).toLong()
        db.close()
        return Integer.parseInt("$success")!=-1
    }

    /** to update */

    fun updateTask(tasks: TaskListModel) : Boolean{
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(taskName,tasks.name)
        values.put(taskName,tasks.details)
        val success=db.update(table_name, values,"$ID=:${tasks.id}", arrayOf()).toLong()
        db.close()
        return Integer.parseInt("$success")!=-1
    }
}