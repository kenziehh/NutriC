package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.features.healthinfo.data.remote.AllergyService
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.AllergyRepository
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetAllergiesUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetHealthInfoUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.ValidateInputStepUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AllergyModule {
  @Provides
  @Singleton
  fun provideAllergyService(retrofit: Retrofit): AllergyService {
    return retrofit.create(AllergyService::class.java)
  }

  @Provides
  @Singleton
  fun provideAllergyRepository(allergyService: AllergyService): AllergyRepository {
    return AllergyRepository(allergyService)
  }

  @Provides
  @Singleton
  fun provideGetAllergiesUseCase(allergyRepository: AllergyRepository): GetAllergiesUseCase {
    return GetAllergiesUseCase(allergyRepository)
  }
}