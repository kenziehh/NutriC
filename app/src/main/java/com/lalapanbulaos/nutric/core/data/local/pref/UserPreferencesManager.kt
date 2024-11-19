package com.lalapanbulaos.nutric.core.data.local.pref

import android.content.SharedPreferences
import com.lalapanbulaos.nutric.core.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val PREFS_NAME = "user_preferences"
        private const val KEY_ACCESS_TOKEN = "access_token"
    }

    fun saveUser(user: User) {
        sharedPreferences.edit().apply {
            putString("user_id", user.id)
            putString("username", user.username)
            apply()
        }
    }

    fun getUser(): User? {
        val userId = sharedPreferences.getString("user_id", null)
        val username = sharedPreferences.getString("username", null)
        return if (userId != null && username != null) {
            User(userId, username)
        } else {
            null
        }
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearAccessToken() {
        sharedPreferences.edit().remove(KEY_ACCESS_TOKEN).apply()
    }
}
