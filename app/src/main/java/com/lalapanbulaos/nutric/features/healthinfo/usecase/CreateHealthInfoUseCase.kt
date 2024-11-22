package com.lalapanbulaos.nutric.features.healthinfo.usecase

import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import javax.inject.Inject

class CreateHealthInfoUseCase @Inject constructor(
    private val healthInfoRepository: HealthInfoRepository
) {
}