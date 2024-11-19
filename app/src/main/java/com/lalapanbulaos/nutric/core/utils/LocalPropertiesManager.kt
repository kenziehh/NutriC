package com.lalapanbulaos.nutric.core.utils

import android.content.Context
import android.util.Log
import java.io.File
import java.util.Properties
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalPropertiesManager @Inject constructor(private val context: Context) {
    private val properties: Properties = Properties()

    init {
        try {
            val localPropertiesFile = File(context.filesDir, "local.properties")
            if (localPropertiesFile.exists()) {
                localPropertiesFile.inputStream().use {
                    properties.load(it)
                }
            }
        } catch (e: Exception) {
            Log.e("LocalPropertiesManager", "Error loading local properties", e)
        }
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return properties.getProperty(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return properties.getProperty(key, defaultValue.toString()).toIntOrNull() ?: defaultValue
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return properties.getProperty(key, defaultValue.toString()).toBoolean()
    }

    fun saveProperty(key: String, value: String) {
        properties[key] = value
        savePropertiesToFile()
    }

    private fun savePropertiesToFile() {
        try {
            val localPropertiesFile = File(context.filesDir, "local.properties")
            localPropertiesFile.outputStream().use {
                properties.store(it, "App Local Properties")
            }
        } catch (e: Exception) {
            Log.e("LocalPropertiesManager", "Error saving local properties", e)
        }
    }

    companion object {
        fun getAPIBaseUrl(localPropertiesManager: LocalPropertiesManager): String {
            return localPropertiesManager.getString("API_BASE_URL", "https://default-url.com")
        }
    }
}