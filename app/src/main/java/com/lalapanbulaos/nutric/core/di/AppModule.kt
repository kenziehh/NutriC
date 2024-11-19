package com.lalapanbulaos.nutric.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  // Define DataStore instance
  private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

  @Provides
  @Singleton
  fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
    return context.dataStore
  }

  @Provides
  @Singleton
  fun provideUserPreferencesManager(dataStore: DataStore<Preferences>): UserPreferencesManager {
    return UserPreferencesManager(dataStore)
  }
}
