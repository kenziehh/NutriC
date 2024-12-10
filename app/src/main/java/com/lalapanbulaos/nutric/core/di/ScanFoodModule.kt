package com.lalapanbulaos.nutric.core.di

import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.features.scan_food.data.remote.FoodPredictService
import com.lalapanbulaos.nutric.features.scan_food.data.remote.MealService
import com.lalapanbulaos.nutric.features.scan_food.data.repository.FoodPredictRepository
import com.lalapanbulaos.nutric.features.scan_food.data.repository.MealLogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ScanFoodModule {
    @Provides
    @Singleton
    fun provideFoodPredictService(retrofit: Retrofit): FoodPredictService {
        return retrofit.create(FoodPredictService::class.java)
    }

    @Provides
    @Singleton
    fun provideFoodPredictRepository(foodPredictService: FoodPredictService): FoodPredictRepository {
        return FoodPredictRepository(foodPredictService)
    }

    @Provides
    @Singleton
    fun provideMealService(retrofit: Retrofit): MealService {
        return retrofit.create(MealService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealLogRepository(mealService: MealService): MealLogRepository {
        return MealLogRepository(mealService)
    }
}