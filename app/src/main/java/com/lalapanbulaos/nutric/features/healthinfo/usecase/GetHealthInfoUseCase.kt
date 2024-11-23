package com.lalapanbulaos.nutric.features.healthinfo.usecase

import android.util.Log
import com.lalapanbulaos.nutric.features.healthinfo.data.model.HealthInfo
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.HealthInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetHealthInfoUseCase @Inject constructor(
    private val healthInfoRepository: HealthInfoRepository
)  {
    fun execute(): Flow<HealthInfo> {
        val healthInfo = healthInfoRepository.getHealthInfo().
                catch { exception ->
                    throw exception
                }.flowOn(
                    Dispatchers.IO
                )

        Log.d("GetHealthInfoUseCase", "Health Info: $healthInfo")
        return healthInfo
    }
}