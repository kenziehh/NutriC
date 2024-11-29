package com.lalapanbulaos.nutric.features.healthinfo.usecase

import android.util.Log
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.HealthInfo
//import com.lalapanbulaos.nutric.core.models.Resource
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import javax.inject.Inject

class GetHealthInfoUseCase @Inject constructor(
    private val healthInfoRepository: HealthInfoRepository,
) {
    suspend fun execute(): Result<HealthInfo> {
        return healthInfoRepository.getHealthInfo()
    }
}
