package com.lalapanbulaos.nutric.features.auth.data.models

data class AuthResponse (
    val access_token: String,
    val userId: String,
    val username: String
)