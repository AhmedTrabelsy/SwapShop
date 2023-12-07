package com.example.swapshop_mobile_version

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("refresh_expires_in") val refreshExpiresIn: Long,
    @SerializedName("refresh_token") val refreshToken: String,
) {

}

data class SignUpResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
) {

}

data class getUserResponse(
    @SerializedName("email") val email: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("attributes") val attributes: Map<String, List<String>>
) {
    val phoneNumber: String?
        get() = attributes["phone_number"]?.firstOrNull()
}