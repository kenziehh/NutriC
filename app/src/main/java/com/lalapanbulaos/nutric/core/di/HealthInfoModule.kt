package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.features.healthinfo.data.remote.HealthInfoService
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
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
    fun provideHealthInfoRepository(healthInfoService: HealthInfoService, userPreferencesManager: UserPreferencesManager): HealthInfoRepository {
        return HealthInfoRepository(healthInfoService, userPreferencesManager)
    }

}