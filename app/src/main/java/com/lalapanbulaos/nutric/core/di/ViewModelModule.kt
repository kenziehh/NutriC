package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.features.auth.usecase.SignInUseCase
import com.lalapanbulaos.nutric.features.auth.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

  @Provides
  fun provideAuthViewModel(signInUseCase: SignInUseCase, signUpUseCase: SignUpUseCase): AuthViewModel {
    return AuthViewModel(signInUseCase, signUpUseCase)
  }
}