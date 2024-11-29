package com.lalapanbulaos.nutric.features.healthinfo.usecase

import com.lalapanbulaos.nutric.core.models.Allergy
import com.lalapanbulaos.nutric.features.healthinfo.data.repository.AllergyRepository
import javax.inject.Inject


class GetAllergiesUseCase @Inject constructor(
  private val allergyRepository: AllergyRepository
) {
  suspend fun execute(): Result<List<Allergy>> {
    return allergyRepository.getAllergies()
  }
}