package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.features.healthinfo.data.remote.HealthInfoService
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.HealthInfoViewModel
import com.lalapanbulaos.nutric.features.healthinfo.usecase.CreateHealthInfoUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetAllergiesUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetDailyTargetUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.GetHealthInfoUseCase
import com.lalapanbulaos.nutric.features.healthinfo.usecase.HealthInfoStepManager
import com.lalapanbulaos.nutric.features.healthinfo.usecase.ValidateInputStepUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HealthInfoModule {
    @Provides
    @Singleton
    fun provideHealthInfoService(retrofit: Retrofit): HealthInfoService {
        return retrofit.create(HealthInfoService::class.java)
    }

    @Provides
    @Singleton
    fun provideHealthInfoRepository(healthInfoService: HealthInfoService): HealthInfoRepository {
        return HealthInfoRepository(healthInfoService)
    }

    @Provides
    @Singleton
    fun provideGetHealthInfoUseCase(healthInfoRepository: HealthInfoRepository): GetHealthInfoUseCase {
        return GetHealthInfoUseCase(healthInfoRepository)
    }

    @Provides
    @Singleton
    fun provideCreateHealthInfoUseCase(healthInfoRepository: HealthInfoRepository): CreateHealthInfoUseCase {
        return CreateHealthInfoUseCase(healthInfoRepository)
    }

    @Provides
    @Singleton
    fun provideGetDailyTargetUseCase(healthInfoRepository: HealthInfoRepository): GetDailyTargetUseCase {
        return GetDailyTargetUseCase(healthInfoRepository)
    }


}