package com.lalapanbulaos.nutric.features.healthinfo.usecase

import com.lalapanbulaos.nutric.features.healthinfo.data.model.Allergy
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.AllergyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetAllergiesUseCase @Inject constructor(
  private val allergyRepository: AllergyRepository
) {
  suspend fun execute(): Result<List<Allergy>> {
    return allergyRepository.getAllergies()
  }
}