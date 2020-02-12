package com.example.myapplication.api

import android.os.SystemClock
import com.example.myapplication.App
import com.example.myapplication.model.Response
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkManager {

    @GET("search/users")
    @Headers("Content-Type: application/json")
    fun getUser(@Query("&q") name: String, @Query("per_page") perPage: Int): Observable<Response>

    companion object {

        fun create(): NetworkManager {

            val builder = OkHttpClient.Builder()

            val interceptor = Interceptor { chain ->
                SystemClock.sleep(2550)
                chain.proceed(chain.request())
            }

            builder.addNetworkInterceptor(interceptor)

            val client = builder.build()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(App.API)
                .client(client)
                .build()

            return retrofit.create(NetworkManager::class.java)
        }
    }
}