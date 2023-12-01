package com.example.swapshop_mobile_version

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GetUserData {
    @FormUrlEncoded
    @POST("admin/master/console/#/android_realm/auth/protocol/openid-connect/token")
    fun getAccessToken(
        @Field("client-id") clientId: String,
        @Field("grant_type") grantType: String,
        @Field("client_secret") clientSecret: String,
        @Field("scope") scope: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<AccessToken>
}