package com.lalapanbulaos.nutric.core.models

import com.google.gson.annotations.SerializedName

data class Article(
    val id: String,
    val title: String,
    val content: String,
    val author: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val imageUrl: String,
)
