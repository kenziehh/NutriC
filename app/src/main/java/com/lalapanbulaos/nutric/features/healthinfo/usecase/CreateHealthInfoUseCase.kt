package com.lalapanbulaos.nutric.features.healthinfo.usecase

import android.util.Log
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import com.lalapanbulaos.nutric.core.models.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfoRequest
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateHealthInfoUseCase @Inject constructor(
    private val healthInfoRepository: HealthInfoRepository,
) {
    suspend fun execute(createHealthInfoRequest: HealthInfoRequest): Result<HealthInfo> {
        return healthInfoRepository.createHealthInfo(createHealthInfoRequest)
    }
}