package com.lalapanbulaos.nutric.features.article.data.remote

import com.lalapanbulaos.nutric.core.network.models.ApiResponse
import com.lalapanbulaos.nutric.features.article.data.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {
    @GET("articles")
    suspend fun getArticles(@Query("term") term: String? = null): Response<ApiResponse<List<ArticleResponse>>>

    @GET("articles/{id}")
    suspend fun getArticleById(
        @Path("id") id: String
    ): Response<ApiResponse<ArticleResponse>>

}