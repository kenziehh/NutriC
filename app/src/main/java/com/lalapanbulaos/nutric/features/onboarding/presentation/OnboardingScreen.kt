package com.lalapanbulaos.nutric.features.onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.CustomTypography
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun OnboardingScreen(
    steps: List<OnboardingStep>,
    currentStepIndex: Int,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.onboarding_background),
            contentDescription = "background",
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 170.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = steps[currentStepIndex].backgroundImage),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .align(Alignment.BottomCenter)
                    .scale(1.2f)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        // Skip Button
        Text(
            text = "Lewati",
            style = NutriCTypography.subHeadingSm,
             modifier = Modifier
                .clickable { onSkip() }
                 .padding(end = 16.dp)
                 .align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp
                )
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Dots Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(steps.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(8.dp)
                            .background(
                                color = if (index == currentStepIndex) Colors.Primary.color40 else Colors.Neutral.color20,
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Step Content
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = steps[currentStepIndex].title,
                    style = CustomTypography.headlineLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = steps[currentStepIndex].description,
                    style = CustomTypography.bodyLarge,
                    color = Colors.Neutral.color30,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            // Next Button
            NutriCButton(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
                text = if (currentStepIndex == steps.size - 1) "Mulai" else "Lanjut"
            )
        }
    }
}

data class OnboardingStep(
    val backgroundImage: Int,
    val title: String,
    val description: String
)

// Usage
@Composable
fun OnboardingStepsScreen(onFinish: () -> Unit, viewModel: AuthViewModel = hiltViewModel()) {
    val steps = listOf(
        OnboardingStep(
            backgroundImage =
            R.drawable.onboarding1,
            title = "Selamat Datang di NutriC!",
            description = "Teman setia untuk pemantauan dan perencanaan gizi Anda!"
        ),
        OnboardingStep(
            backgroundImage = R.drawable.onboarding2,
            title = "Pantau Asupan Anda",
            description = "Dapatkan wawasan tentang pola makan Anda setiap hari."
        ),
        OnboardingStep(
            backgroundImage = R.drawable.onboarding3,
            title = "Rencanakan Gizi Anda",
            description = "Atur tujuan dan rencana gizi yang sesuai untuk kebutuhan Anda."
        )
    )
    var currentStep by remember { mutableStateOf(0) }

    fun onOnboarded() {
        viewModel.updateOnboardedState()
        onFinish()
    }

    OnboardingScreen(
        steps = steps,
        currentStepIndex = currentStep,
        onNext = {
            if (currentStep < steps.size - 1) {
                currentStep++
            } else {
                onOnboarded()
            }
        },
        onSkip = { onOnboarded() }
    )
}
