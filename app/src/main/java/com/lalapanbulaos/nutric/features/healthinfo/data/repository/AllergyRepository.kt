package com.lalapanbulaos.nutric.features.healthinfo.data.repository

import com.lalapanbulaos.nutric.features.healthinfo.data.model.Allergy
import com.lalapanbulaos.nutric.features.healthinfo.data.remote.AllergyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AllergyRepository @Inject constructor(private val allergyService: AllergyService) {
  fun getAllergies(): Flow<List<Allergy>> = flow  {
    try {
      val response = allergyService.getAllergies()
      if (response.isSuccessful) {
        response.body()?.data?.let { allergies ->
          emit(allergies)
        } ?: throw Exception("Response body is null")
      } else {
        throw Exception("API call failed with code: ${response.code()}")
      }
    } catch (e: Exception) {
      throw e
    }
  }
}