package com.stayspotter.common.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stayspotter.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constant.STAYSPOTTER_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}