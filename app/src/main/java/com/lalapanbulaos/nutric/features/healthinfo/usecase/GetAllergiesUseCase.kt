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
  fun execute(): Flow<List<Allergy>> {
    return allergyRepository.getAllergies()
      .catch { exception ->
        throw exception
      }
      .flowOn(Dispatchers.IO)
  }
}