package com.example.swapshop_mobile_version

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

data class LoginData(
    val username: String,
    val password: String
)

data class SignupData(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)

interface GetUserData {
    @POST("AUTH-SERVICE/login")
    fun getAccessToken(@Body loginData: LoginData): Call<AccessToken>


    @POST("AUTH-SERVICE/signup")
    fun Signup(@Body SignupData: SignupData): Call<SignUpResponse>
}