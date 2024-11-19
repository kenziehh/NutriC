package com.lalapanbulaos.nutric.core.di

import android.content.Context
import android.content.SharedPreferences
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
    return context.getSharedPreferences(UserPreferencesManager.PREFS_NAME, Context.MODE_PRIVATE)
  }

  @Provides
  fun provideUserPreferencesManager(sharedPreferences: SharedPreferences): UserPreferencesManager {
    return UserPreferencesManager(sharedPreferences)
  }
}
