package com.lalapanbulaos.nutric.core.models

data class ScanHistory(
    val id: String,
    val userId: String,
    val foodId: String,
    val type: String,
    val createdAt: String, // ISO8601 format
    val updatedAt: String
)

