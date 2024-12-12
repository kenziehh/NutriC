package com.lalapanbulaos.nutric.features.article.data.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    val id: String,
    val title: String,
    val content: String,
    val author: Author,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val imageUrl: String,
)

data class Author (
    val username: String,
)