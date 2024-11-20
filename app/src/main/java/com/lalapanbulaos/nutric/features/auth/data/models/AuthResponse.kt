package com.lalapanbulaos.nutric.features.auth.data.models

import com.lalapanbulaos.nutric.core.models.User

data class AuthResponse(
    val access_token: String,
    val user: User
)
