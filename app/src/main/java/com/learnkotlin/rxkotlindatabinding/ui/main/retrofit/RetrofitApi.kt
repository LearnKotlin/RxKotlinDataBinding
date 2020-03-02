package com.learnkotlin.rxkotlindatabinding.ui.main.retrofit

import com.learnkotlin.rxkotlindatabinding.ui.main.model.Todo
import com.learnkotlin.rxkotlindatabinding.ui.main.model.User
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Path

interface RetrofitApi {

    @GET("todos")
    fun todos(): Observable<List<Todo>>

    @GET("users/{userid}")
    fun user(@Path("userid")  userid: Int): Observable<User>

}