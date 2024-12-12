package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.features.article.data.remote.ArticleService
import com.lalapanbulaos.nutric.features.article.data.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ArticleModule {
    @Provides
    @Singleton
    fun provideArticleService(retrofit: Retrofit): ArticleService {
        return retrofit.create(ArticleService::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(articleService: ArticleService): ArticleRepository {
        return ArticleRepository(articleService)
    }
}