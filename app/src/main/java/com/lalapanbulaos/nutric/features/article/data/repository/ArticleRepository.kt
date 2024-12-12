package com.lalapanbulaos.nutric.features.article.data.repository

import com.lalapanbulaos.nutric.core.data.repository.BaseRepository
import com.lalapanbulaos.nutric.features.article.data.model.ArticleResponse
import com.lalapanbulaos.nutric.features.article.data.remote.ArticleService
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val articleService: ArticleService
): BaseRepository() {
    suspend fun getAllArticles(term: String? = null) : Result<List<ArticleResponse>> {
        return executeApiCall {
            articleService.getArticles(term)
        }
    }

    suspend fun getArticleById(id: String): Result<ArticleResponse> {
        return executeApiCall {
            articleService.getArticleById(id)
        }
    }
}