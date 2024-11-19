package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.SignInViewModel
import com.lalapanbulaos.nutric.features.auth.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

  @Provides
  fun provideSignInViewModel(signInUseCase: SignInUseCase): SignInViewModel {
    return SignInViewModel(signInUseCase)
  }
}