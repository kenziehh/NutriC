package com.lalapanbulaos.nutric.core.di

//import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
//import com.lalapanbulaos.nutric.features.scan_food.data.remote.FoodPredictService
//import com.lalapanbulaos.nutric.features.scan_food.data.repository.FoodPredictRepository
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//class ScanFoodModule {
//    @Provides
//    @Singleton
//    fun provideFoodPredictService(retrofit: Retrofit): FoodPredictService {
//        return retrofit.create(FoodPredictService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideFoodPredictRepository(foodPredictService: FoodPredictService, userPreferencesManager: UserPreferencesManager): FoodPredictRepository {
//        return FoodPredictRepository(foodPredictService, userPreferencesManager)
//    }
//
//
//}