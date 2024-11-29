package com.lalapanbulaos.nutric.features.healthinfo.usecase

import android.util.Log
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import javax.inject.Inject

class GetHealthInfoUseCase @Inject constructor(
    private val healthInfoRepository: HealthInfoRepository,
    private val userPreferencesManager: UserPreferencesManager
) {
    suspend fun execute(): Result<HealthInfo> {
        val token = userPreferencesManager.getBearerToken()

        if (token == null) {
            return Result.failure(Exception("Token is null"))
        } else {
            Log.d("GetHealthInfoUseCase", "Token: $token")
            return healthInfoRepository.getHealthInfo(token)
        }
    }
}
