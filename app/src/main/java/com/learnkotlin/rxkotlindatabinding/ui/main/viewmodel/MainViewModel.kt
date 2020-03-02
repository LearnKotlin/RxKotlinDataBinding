package com.learnkotlin.rxkotlindatabinding.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learnkotlin.rxkotlindatabinding.ui.main.model.Todo
import com.learnkotlin.rxkotlindatabinding.ui.main.model.User
import com.learnkotlin.rxkotlindatabinding.ui.main.retrofit.RetrofitApi
import com.learnkotlin.rxkotlindatabinding.ui.main.retrofit.RetrofitClient
import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import okhttp3.Dispatcher
import java.util.logging.Handler

class MainViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var apiClient: RetrofitApi = RetrofitClient.getClient().create(RetrofitApi::class.java)
    val  todos = MutableLiveData<List<Todo>>()

    init {

        // Set Subscriber for todolist fetch and assignee update
        val todoSubscriber = apiClient.todos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                    list -> todoObservable(list)
            }.subscribe({ todo ->
                println("Todo is ${todos.value}" )

                todos.postValue(todos.value)
            }, { throwable ->
                println("Error fetching todo item ${throwable}" )
            }, {
                println("Completed todo list fetch")
            })

        addDisposable(todoSubscriber)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun todoObservable(list: List<Todo>) : Observable<Todo> {
        todos.postValue(list)

        return Observable.fromIterable(list).flatMap {
            //Simulating delay
            Thread.sleep(400)
            Observable.create<Todo> { e -> emit(it, e) }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    }

    private fun emit(todo: Todo, e: Emitter<Todo>){

        var userSubscriber = userObservable(todo).subscribe({ user ->
            todo.assignedUsername = user.name
            e.onNext(todo)
        }, { throwable ->
            println("Error fetching user ${throwable}" )
        })
        addDisposable(userSubscriber)

    }

    private fun userObservable(todo: Todo) : Observable<User>{

        // Set a subscriber for assignee fetch
        return apiClient
            .user(todo.userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    private fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

}
