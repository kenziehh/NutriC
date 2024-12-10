package com.lalapanbulaos.nutric.features.scan_food.usecase

import com.lalapanbulaos.nutric.core.models.DailyTarget
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import javax.inject.Inject

class GetUserDailyNutritionTarget @Inject constructor(
    private val healthInfoRepository: HealthInfoRepository,
) {
    suspend fun execute(): Result<DailyTarget> {
        return healthInfoRepository.getDailyTarget()
    }
}