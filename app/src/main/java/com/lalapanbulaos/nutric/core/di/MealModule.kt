package com.lalapanbulaos.nutric.core.di


import com.lalapanbulaos.nutric.features.meal.data.remote.MealService
import com.lalapanbulaos.nutric.features.meal.data.repository.MealRepository
import com.lalapanbulaos.nutric.features.meal.usecase.GetMealUseCase
import com.lalapanbulaos.nutric.features.meal.usecase.GetTotalMacroNutrientUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MealModule {
    @Provides
    @Singleton
    fun provideMealService(retrofit: Retrofit): MealService {
        return retrofit.create(MealService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealRepository(mealService: MealService): MealRepository {
        return MealRepository(mealService)
    }

    @Provides
    @Singleton
    fun provideGetMealUseCase(mealRepository: MealRepository): GetMealUseCase {
        return GetMealUseCase(mealRepository)
    }

    @Provides
    @Singleton
    fun provideGetMacronutrientUseCase(mealRepository: MealRepository): GetTotalMacroNutrientUseCase {
        return GetTotalMacroNutrientUseCase(mealRepository)
    }

}