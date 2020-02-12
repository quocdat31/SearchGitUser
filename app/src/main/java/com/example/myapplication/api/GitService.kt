package com.example.myapplication.api

import android.os.SystemClock
import com.example.myapplication.App
import com.example.myapplication.model.GitUser
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.StringBuilder

interface GitService {

    @GET("search/users?")
    @Headers("Content-Type: application/json")

    fun fetchGitUSer(
        @Query("per_page") perPage: Int,
        @Query("q") name: String
    ): Call<List<GitUser>>

    companion object {

        fun create(): GitService? {

            val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl(App.API)
                .build()

            return retrofit.create(GitService::class.java)
        }
    }
}

