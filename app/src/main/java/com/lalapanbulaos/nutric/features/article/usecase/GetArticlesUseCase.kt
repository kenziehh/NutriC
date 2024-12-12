package com.lalapanbulaos.nutric.features.article.usecase

import com.lalapanbulaos.nutric.core.models.Article
import com.lalapanbulaos.nutric.features.article.data.model.ArticleResponse
import com.lalapanbulaos.nutric.features.article.data.repository.ArticleRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
) {
    suspend fun execute(query: String? = null): Result<List<Article>> {
        val response = articleRepository.getAllArticles(query)

        if (response.isSuccess) {
            val articleResponses = response.getOrNull()

            return if (articleResponses != null) {
                Result.success(mapArticleResponsesToArticles(articleResponses))
            } else {
                Result.failure(Exception("Articles response was null"))
            }
        } else {
            val errorMessage = response.exceptionOrNull()?.message ?: "Unknown error"
            return Result.failure(Exception(errorMessage))
        }
    }

    suspend fun executeSingleArticle(articleId: String): Result<Article> {
        val response = articleRepository.getArticleById(articleId)

        if (response.isSuccess) {
            val articleResponse = response.getOrNull()

            return if (articleResponse != null) {
                Result.success(
                    Article(
                        id = articleResponse.id,
                        title = articleResponse.title,
                        content = articleResponse.content,
                        author = articleResponse.author.username,
                        createdAt = articleResponse.createdAt,
                        updatedAt = articleResponse.updatedAt,
                        imageUrl = articleResponse.imageUrl
                    )
                )
            } else {
                Result.failure(Exception("Article response was null"))
            }
            } else {
            val errorMessage = response.exceptionOrNull()?.message ?: "Unknown error"
            return Result.failure(Exception(errorMessage))
        }
    }

    private fun mapArticleResponsesToArticles(articleResponses: List<ArticleResponse>): List<Article> {
        return articleResponses.map { articleResponse ->
            Article(
                id = articleResponse.id,
                title = articleResponse.title,
                content = articleResponse.content,
                author = articleResponse.author.username,
                createdAt = articleResponse.createdAt,
                updatedAt = articleResponse.updatedAt,
                imageUrl = articleResponse.imageUrl
            )
        }
    }
}