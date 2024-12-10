package com.lalapanbulaos.nutric.features.healthinfo.usecase

import com.lalapanbulaos.nutric.features.healthinfo.data.model.DailyTarget
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import javax.inject.Inject

class GetDailyTargetUseCase @Inject constructor(
    private val healthInfoRepository: HealthInfoRepository,
) {
    suspend fun execute(): Result<DailyTarget> {
        return healthInfoRepository.getDailyTarget()
    }
}
