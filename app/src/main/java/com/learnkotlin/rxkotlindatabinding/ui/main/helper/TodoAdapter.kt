package com.learnkotlin.rxkotlindatabinding.ui.main.helper

import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.learnkotlin.rxkotlindatabinding.R
import com.learnkotlin.rxkotlindatabinding.databinding.TodoItemBinding
import com.learnkotlin.rxkotlindatabinding.ui.main.model.Todo


class TodoAdapter(var todoList: List<Todo>) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
         ViewHolder(DataBindingUtil.bind(LayoutInflater.from(parent.context).inflate(R.layout.todo_item, null)))

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateTodo(todoList.get(position))
    }

    fun updateList(todoList: List<Todo>){
        this.todoList = todoList
        notifyDataSetChanged()
    }

    class ViewHolder (val binding: TodoItemBinding?) : RecyclerView.ViewHolder(binding!!.root) {
        fun updateTodo(todo: Todo){
            binding?.todo = todo
            binding?.executePendingBindings()
        }
    }
}