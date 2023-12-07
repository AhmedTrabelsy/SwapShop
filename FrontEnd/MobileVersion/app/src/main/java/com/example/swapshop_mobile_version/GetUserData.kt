package com.example.swapshop_mobile_version

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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

data class updateProfileDate(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
)


interface GetUserData {
    @POST("AUTH-SERVICE/login")
    fun getAccessToken(@Body loginData: LoginData): Call<AccessToken>


    @POST("AUTH-SERVICE/signup")
    fun Signup(@Body SignupData: SignupData): Call<SignUpResponse>

    @GET("AUTH-SERVICE/user")
    fun getData(@Header("Authorization") token: String): Call<getUserResponse>

    @PUT("AUTH-SERVICE/update")
    fun updateProfile(@Header("Authorization") token: String, @Body updateProfileDate:updateProfileDate): Call<getUserResponse>

}