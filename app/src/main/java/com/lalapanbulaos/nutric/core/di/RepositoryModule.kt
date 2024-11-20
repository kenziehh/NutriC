package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.features.auth.data.remote.AuthService
import com.lalapanbulaos.nutric.features.auth.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

  @Provides
  @Singleton
  fun provideAuthRepository(authService: AuthService): AuthRepository {
    return AuthRepository(authService)
  }
}