package com.example.swapshop_mobile_version

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("refresh_expires_in") val refreshExpiresIn: Long,
    @SerializedName("refresh_token") val refreshToken: Long,
    @SerializedName("id_token") val idToken: Long,
    @SerializedName("session_state") val sessionState: Long,
    @SerializedName("scope") val scope: Long
) {

}
