package com.stayspotter.common.api

import com.stayspotter.model.FavouriteStay
import com.stayspotter.model.Stay
import com.stayspotter.model.StayRequestDto
import com.stayspotter.model.UserLoginDto
import com.stayspotter.model.UserRegisterDto
import com.stayspotter.model.UserRegisterResponseDto
import com.stayspotter.model.UserStatsDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("user")
    fun login(@Body userLoginDto: UserLoginDto): Call<String>

    @Headers("Content-Type: application/json")
    @POST("user/new")
    fun register(@Body userRegisterDto: UserRegisterDto): Call<UserRegisterResponseDto>

    @Headers("Content-Type: application/json")
    @POST("stay")
    fun findStay(
        @Body stayRequestDto: StayRequestDto,
        @Header("Authorization") bearerToken: String
    ): Call<List<Stay>>

    @Headers("Content-Type: application/json")
    @GET("stay/favourite")
    fun getFavourites(
        @Header("Authorization") bearerToken: String
    ): Call<List<FavouriteStay>>

    @Headers("Content-Type: application/json")
    @POST("stay/favourite")
    fun saveToFavourite(
        @Body favouriteStay: FavouriteStay,
        @Header("Authorization") bearerToken: String
    ): Call<Unit>

    @Headers("Content-Type: application/json")
    @GET("stats")
    fun increaseCheckedOutStays(
        @Header("Authorization") bearerToken: String
    ): Call<Unit>

    @Headers("Content-Type: application/json")
    @GET("stats/user")
    fun getUserStats(
        @Header("Authorization") bearerToken: String
    ): Call<UserStatsDto>
}