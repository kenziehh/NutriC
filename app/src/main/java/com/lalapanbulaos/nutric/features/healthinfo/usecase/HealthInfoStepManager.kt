package com.lalapanbulaos.nutric.features.healthinfo.usecase

import javax.inject.Inject

class HealthInfoStepManager @Inject constructor(
    private val validateInputStepUseCase: ValidateInputStepUseCase
) {
    fun getSteps(): List<HealthInfoInputStep> = listOf(
        HealthInfoInputStep(
            id = "age",
            title = "Berapa umur kamu?",
            description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
        ),
        HealthInfoInputStep(
            id = "height",
            title = "Berapa tinggi badan kamu?",
            description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
        ),
        HealthInfoInputStep(
            id = "weight",
            title = "Berapa berat badan kamu?",
            description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
        ),
        HealthInfoInputStep(
            id = "allergies",
            title = "Apakah kamu punya alergi?",
            description = "Kami akan menggunakan ini untuk memberikan kamu pengalaman yang lebih baik dalam menjaga nutrisimu",
            notes = "Bisa memilih lebih dari 1",
        )
    )

    fun canMoveToNextStep(currentStep: Int, inputState: com.lalapanbulaos.nutric.features.healthinfo.presentation.viewmodel.InputState): Boolean {
        return validateInputStepUseCase.isAllowedNext(
            getSteps()[currentStep].id,
            inputState
        )
    }

    fun getTotalSteps() = getSteps().size

    fun isLastStep(currentStep: Int) = currentStep == getSteps().size - 1

    fun isFirstStep(currentStep: Int) = currentStep == 0
}

data class HealthInfoInputStep(
    val id: String,
    val title: String,
    val description: String,
    val notes: String? = null,
)