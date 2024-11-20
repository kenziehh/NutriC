package com.lalapanbulaos.nutric.core.network.models

data class ApiResponse<T> (
    val success: Boolean,
    val message: String,
    val data: T
)