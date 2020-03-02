package com.learnkotlin.rxkotlindatabinding.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.learnkotlin.rxkotlindatabinding.R
import com.learnkotlin.rxkotlindatabinding.ui.main.helper.TodoAdapter
import com.learnkotlin.rxkotlindatabinding.ui.main.viewmodel.MainViewModel


class MainFragment : Fragment(R.layout.main_fragment) {


    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.todo_listview)
        val adapter = TodoAdapter(viewModel.todos.value ?: listOf())

        recyclerView?.adapter = adapter

        viewModel.todos.observe(activity as AppCompatActivity, Observer {
            viewModel.todos.value?.let {
                adapter.updateList(it)
            }
        })

    }


}
