package com.example.sqlite_database

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite_database.Model.TaskListModel

class TaskListAdapter(tasklist: List<TaskListModel>, internal var context: Context) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    internal var tasklist: List<TaskListModel> = ArrayList()

    init {
        this.tasklist = tasklist
    }


    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.txt_name)
        var details: TextView = view.findViewById(R.id.txt_details)
        var edit : Button = view.findViewById(R.id.btn_Edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclertasklist, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasklist.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
             val tasks= tasklist[position]
        holder.name.text=tasks.name
        holder.details.text=tasks.details
holder.edit.setOnClickListener{
    val intent = Intent(context,AddTask::class.java)
    intent.putExtra("Mode","E")
    intent.putExtra(Constants.tableColId,tasks.id)
    context.startActivity(intent)
}

    }
}