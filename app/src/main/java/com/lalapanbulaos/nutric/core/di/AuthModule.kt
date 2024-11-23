package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.features.auth.data.remote.AuthService
import com.lalapanbulaos.nutric.features.auth.data.repository.AuthRepository
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.features.auth.usecase.SignInUseCase
import com.lalapanbulaos.nutric.features.auth.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
  @Provides
  @Singleton
  fun provideAuthService(retrofit: Retrofit): AuthService {
    return retrofit.create(AuthService::class.java)
  }

  @Provides
  @Singleton
  fun provideAuthRepository(authService: AuthService): AuthRepository {
    return AuthRepository(authService)
  }

  @Provides
  fun provideSignInUseCase(authRepository: AuthRepository, userPreferencesManager: UserPreferencesManager): SignInUseCase {
    return SignInUseCase(authRepository, userPreferencesManager)
  }

  @Provides
  fun provideSignUpUseCase(authRepository: AuthRepository, userPreferencesManager: UserPreferencesManager): SignUpUseCase {
    return SignUpUseCase(authRepository, userPreferencesManager)
  }

  @Provides
  fun provideAuthViewModel(signInUseCase: SignInUseCase, signUpUseCase: SignUpUseCase, userPreferencesManager: UserPreferencesManager): AuthViewModel {
    return AuthViewModel(signInUseCase, signUpUseCase, userPreferencesManager)
  }
}