package com.example.swapshop_mobile_version

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class LoginData(
    val username: String,
    val password: String
)

interface GetUserData {
    @POST("AUTH-SERVICE/login")
    fun getAccessToken(@Body loginData: LoginData): Call<AccessToken>
}