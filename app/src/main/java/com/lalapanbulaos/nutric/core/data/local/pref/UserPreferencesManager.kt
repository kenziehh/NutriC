package com.lalapanbulaos.nutric.core.data.local.pref

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lalapanbulaos.nutric.core.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = user.id
            preferences[KEY_USERNAME] = user.username
        }
    }

    val user: Flow<User?> = dataStore.data.map { preferences ->
        val userId = preferences[KEY_USER_ID]
        val username = preferences[KEY_USERNAME]
        if (userId != null && username != null) {
            User(userId, username)
        } else {
            null
        }
    }.distinctUntilChanged()

    suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USERNAME)
        }
    }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_ACCESS_TOKEN] = token
        }
        Log.d("UserPreferencesManager", "Access token saved: $token")
    }

    val accessToken: Flow<String?> = dataStore.data
        .catch { exception ->
            // Handle any errors reading data
            Log.e("UserPreferencesManager", "Error reading preferences", exception)
            emit(emptyPreferences())
        }
        .map { preferences ->
            preferences[KEY_ACCESS_TOKEN]
        }.distinctUntilChanged()

    suspend fun clearAccessToken() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_ACCESS_TOKEN)
        }
    }

    suspend fun getBearerToken(): String? {
        val token = accessToken.first()
        if (token != null) {
            return "Bearer $token"
        } else {
            return null
        }
    }
}
