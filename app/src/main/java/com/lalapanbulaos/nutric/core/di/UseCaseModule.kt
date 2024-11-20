package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.features.auth.data.repository.AuthRepository
import com.lalapanbulaos.nutric.features.auth.usecase.SignInUseCase
import com.lalapanbulaos.nutric.features.auth.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

  @Provides
  fun provideSignInUseCase(authRepository: AuthRepository, userPreferencesManager: UserPreferencesManager): SignInUseCase {
    return SignInUseCase(authRepository, userPreferencesManager)
  }

  @Provides
  fun provideSignUpUseCase(authRepository: AuthRepository, userPreferencesManager: UserPreferencesManager): SignUpUseCase {
    return SignUpUseCase(authRepository, userPreferencesManager)
  }
}