package com.stayspotter.common.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stayspotter.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

object RetrofitClient {

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(Duration.ofSeconds(60))
        .connectTimeout(Duration.ofSeconds(60))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constant.STAYSPOTTER_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}