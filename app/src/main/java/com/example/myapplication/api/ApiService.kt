package com.example.myapplication.api

import android.telecom.Call
import com.example.myapplication.model.GitUser
import com.example.myapplication.model.Response
import com.example.myapplication.ultis.log
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("&q") value: String): Observable<Response>

}