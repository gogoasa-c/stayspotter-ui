package com.stayspotter.common.api

import com.stayspotter.model.Stay
import com.stayspotter.model.StayRequestDto
import com.stayspotter.model.UserLoginDto
import com.stayspotter.model.UserRegisterDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("user")
    fun login(@Body userLoginDto: UserLoginDto): Call<String>

    @Headers("Content-Type: application/json")
    @POST("user/new")
    fun register(@Body userRegisterDto: UserRegisterDto): Call<String>

    @Headers("Content-Type: application/json")
    @POST("stay")
    fun findStay(
        @Body stayRequestDto: StayRequestDto,
        @Header("Authorization") bearerToken: String
    ): Call<List<Stay>>
}