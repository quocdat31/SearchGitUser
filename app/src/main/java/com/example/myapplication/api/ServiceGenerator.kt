package com.example.myapplication.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//object ServiceGenerator {
//    private const val BASE_URL = "https://api.github.com/"
//    private const val BASE_URL_ = "https://api.darksky.net/"
//    private var retrofit: Retrofit? = null
//    private val builder = Retrofit.Builder().baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//    // Khai baos log de lay ra gia tri cua request
//    private val httpLoggingInterceptor: HttpLoggingInterceptor =
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    private val okHttpClientBuilder =
//        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
//    private val okHttpClient = okHttpClientBuilder.build()
//    fun <T> createService(serviceClass: Class<T>?): T {
//        if (retrofit == null) {
//            retrofit =
//                builder.client(okHttpClient).build()
//        }
//        return retrofit!!.create(serviceClass)
//    }
//}