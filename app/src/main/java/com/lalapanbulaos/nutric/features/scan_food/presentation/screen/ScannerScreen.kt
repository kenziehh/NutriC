package com.lalapanbulaos.nutric.features.scan_food.presentation.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.core.utils.checkPermissionFor
import com.lalapanbulaos.nutric.features.scan_food.presentation.viewmodel.ScannerUiState
import com.lalapanbulaos.nutric.features.scan_food.presentation.viewmodel.ScannerViewModel
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.component.NutrientProgressBar
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.CustomTypography
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.ceil

@Composable
fun ScannerScreen(
    onGoBack: () -> Unit,
) {
    val context = LocalContext.current
    var isCameraPermissionGranted by remember {
        mutableStateOf(
            checkPermissionFor(
                context,
                android.Manifest.permission.CAMERA
            )
        )
    }

    val permissionState = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            isCameraPermissionGranted = isGranted
        }
    )

    if (isCameraPermissionGranted) {
        CameraPreview(
            onGoBack
        )
    } else {
        SideEffect {
            permissionState.launch(android.Manifest.permission.CAMERA)
        }
    }
}

@Composable
fun CameraPreview(
    onGoBack: () -> Unit,
    scannerViewModel: ScannerViewModel = hiltViewModel()
) {
    val uiState = scannerViewModel.uiState.collectAsState()
    val lensFacing = uiState.value.lensFacing
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()
    val previewView = remember {
        PreviewView(context)
    }
    val imageCapture = remember {
        ImageCapture.Builder().build()
    }
    val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxWidth()
            )

            IconButton(
                onClick = { onGoBack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp, start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter).fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)  // 80% of screen width
                        .fillMaxHeight(0.4f)  // Increase container height to see animation
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    // Animated line scan effect
                    val transition = rememberInfiniteTransition(label = "")

                    val containerHeight = LocalDensity.current.run {
                        LocalConfiguration.current.screenHeightDp.dp.toPx() * 0.4f
                    }

                    val translateY by transition.animateValue(
                        initialValue = 0.dp,
                        targetValue = with(LocalDensity.current) { containerHeight.toDp() },
                        typeConverter = Dp.VectorConverter,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 3000, easing = EaseInOut),
                            repeatMode = RepeatMode.Reverse  // Add this to bounce back and forth
                        ),
                        label = ""
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = translateY)
                            .height(4.dp)
                            .background(Colors.Secondary.color40)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier.align(Alignment.CenterStart).clip(
                            CircleShape
                        ).background(Colors.Secondary.color50).padding(8.dp).clickable {
                            scannerViewModel.onToggleCameraLensClicked()
                        }

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camerarotate),
                            contentDescription = "switch-camera",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Button(
                        enabled = !uiState.value.isScanning,
                        onClick = {
                            val outputOptions = ImageCapture.OutputFileOptions.Builder(
                                File(
                                    context.cacheDir,
                                    "captured_image.jpg"
                                )
                            ).build()
                            imageCapture.takePicture(
                                outputOptions,
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                        Log.d("CameraX", "Capture success")
                                        val imageFile =
                                            outputFileResults.savedUri?.path?.let { File(it) }
                                        imageFile?.let {
                                            scannerViewModel.onCaptureClicked(it)
                                        }
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        Log.e("CameraX", "Capture failed", exception)
                                    }
                                }
                            )
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Colors.Secondary.color50),
                        modifier = Modifier
                            .size(72.dp)
                            .align(Alignment.Center)
                    ) {
                        if (uiState.value.isScanning) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                            return@Button
                        }
                        Image(
                            painter = painterResource(id = R.drawable.scan),
                            contentDescription = "scan-food",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }


                AnimatedVisibility(
                    visible = uiState.value.isScanSuccess,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    ScanResultBottomSheet(
                        modifier = Modifier,
                        uiState = uiState.value,
                        onMealSubmit = {
                            scannerViewModel.onAddMealClicked()
                        }
                    )
                }


            }
        }
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

data class MacroItem(
    val label: String,
    val foodInfoMacro: Int,
    val dailyTotalMacro: Int,
    val dailyTarget: Int
)

@Composable
fun ScanResultBottomSheet(
    modifier: Modifier,
    uiState: ScannerUiState,
    onMealSubmit: () -> Unit,
) {
    val context = LocalContext.current
    val foodName = if (uiState.isScanSuccess) uiState.foodName else "Predicting..."
    val hasAllergy = uiState.hasAllergy
    val showAllergyToast = remember { mutableStateOf(false) }

    LaunchedEffect(hasAllergy) {
        if (hasAllergy) {
            showAllergyToast.value = true
        }
    }

    if (showAllergyToast.value) {
        Toast.makeText(
            context,
            "Alergi Terdeteksi : ${uiState.foodInfo?.allergens?.joinToString { it.name }}",
            Toast.LENGTH_SHORT
        ).show()
        showAllergyToast.value = false
    }

    val gridItems = remember(
        uiState.foodInfo?.foodMacroNutrient,
        uiState.dailyTarget
    ) {
        listOf(
            MacroItem(
                "Kalori",
                uiState.foodInfo?.foodMacroNutrient?.calories?.toInt() ?: 1,
                uiState.dailyTotalMacros?.calories?.toInt() ?: 1,
                uiState.dailyTarget?.calories?.toInt() ?: 1 // Fallback to 0 if dailyTarget is null
            ),
            MacroItem(
                "Protein",
                uiState.foodInfo?.foodMacroNutrient?.protein?.toInt() ?: 1,
                uiState.dailyTotalMacros?.protein?.toInt() ?: 1,
                uiState.dailyTarget?.protein?.toInt() ?: 1
            ),
            MacroItem(
                "Lemak",
                uiState.foodInfo?.foodMacroNutrient?.fat?.toInt() ?: 1,
                uiState.dailyTotalMacros?.fat?.toInt() ?: 1,
                uiState.dailyTarget?.fat?.toInt() ?: 1
            ),
            MacroItem(
                "Karbohidrat",
                uiState.foodInfo?.foodMacroNutrient?.carbohydrates?.toInt() ?: 1,
                uiState.dailyTotalMacros?.carbohydrates?.toInt() ?: 1,
                uiState.dailyTarget?.carbohydrates?.toInt() ?: 1
            ),
            MacroItem(
                "Serat",
                uiState.foodInfo?.foodMacroNutrient?.fiber?.toInt() ?: 1,
                uiState.dailyTotalMacros?.fiber?.toInt() ?: 1,
                uiState.dailyTarget?.fiber?.toInt() ?: 1
            ),
            MacroItem(
                "Gula",
                uiState.foodInfo?.foodMacroNutrient?.sugar?.toInt() ?: 1,
                uiState.dailyTotalMacros?.sugar?.toInt() ?: 1,
                uiState.dailyTarget?.sugar?.toInt() ?: 1
            )
        )
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
            .safeDrawingPadding()
            .padding(
                bottom = 16.dp,
                top = 24.dp,
                start = 16.dp,
                end = 16.dp
            ),
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Makanan",
                    style = NutriCTypography.subHeadingXs,
                    color = Colors.Neutral.color50
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(foodName ?: "", style = NutriCTypography.subHeadingLg)
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (hasAllergy) {
                Box(
                    modifier = Modifier
                        .background(Colors.Danger.color10, RoundedCornerShape(8.dp))
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .width(80.dp)
                ) {
                    Text(
                        "Alergi Terdeteksi",
                        fontSize = 12.sp,
                        color = Colors.Danger.color40,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(22.dp))

        if (uiState.isPredictSuccess) {
            LazyVerticalGrid(
//                modifier = Modifier.height(95.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(gridItems.size) { item ->
                    NutrientProgressBar(
                        label = gridItems[item].label,
                        current = gridItems[item].dailyTotalMacro,
                        currentPlusScannedFood = gridItems[item].dailyTotalMacro + gridItems[item].foodInfoMacro,
                        goal = gridItems[item].dailyTarget,
                        imageResource = R.drawable.calories
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Colors.Primary.color40
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        NutriCButton(
            enabled = uiState.isPredictSuccess && !uiState.isMealSubmitting,
            onClick = {
                uiState.foodInfo?.let {
                    onMealSubmit()
                }
            },
            content = {
                if (uiState.isMealSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Colors.Primary.color10
                    )
                } else {
                    Text("Tambahkan Aktivitas", style = CustomTypography.labelLarge)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}