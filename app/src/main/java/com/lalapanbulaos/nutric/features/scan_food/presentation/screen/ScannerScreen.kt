package com.lalapanbulaos.nutric.features.scan_food.presentation.screen

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        scannerViewModel.onToggleCameraLensClicked()
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.Secondary.color50),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.scan),
                        contentDescription = "switch-camera",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Button(
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
                        .padding(bottom = 16.dp)
                        .size(72.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.scan),
                        contentDescription = "scan-food",
                        modifier = Modifier.size(24.dp)
                    )
                }

                if (uiState.value.isScanSuccess) {
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
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }

@Composable
fun ScanResultBottomSheet(
    modifier: Modifier,
    uiState: ScannerUiState,
    onMealSubmit: () -> Unit,
) {
    val foodName = if (uiState.isScanSuccess) uiState.foodName else "Predicting..."
    val hasAllergy = uiState.hasAllergy

    val gridItems = remember(
        uiState.foodInfo?.foodMacroNutrient,
        uiState.dailyTarget
    ) {
        listOf(
            "Karbohidrat" to (uiState.foodInfo?.foodMacroNutrient?.carbohydrates to uiState.dailyTarget?.carbohydrates),
            "Protein" to (uiState.foodInfo?.foodMacroNutrient?.protein to uiState.dailyTarget?.protein),
            "Lemak" to (uiState.foodInfo?.foodMacroNutrient?.fat to uiState.dailyTarget?.fat),
            "Kalori" to (uiState.foodInfo?.foodMacroNutrient?.calories to uiState.dailyTarget?.calories),
            "Serat" to (uiState.foodInfo?.foodMacroNutrient?.fiber to uiState.dailyTarget?.fiber),
            "Gula" to (uiState.foodInfo?.foodMacroNutrient?.sugar to uiState.dailyTarget?.sugar)
        )
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
            .safeDrawingPadding()
            .padding(
                bottom = 0.dp,
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
            Column {
                Text(
                    "Makanan",
                    style = NutriCTypography.subHeadingXs,
                    color = Colors.Neutral.color50
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(foodName ?: "", style = NutriCTypography.headingSm)
            }

            if (hasAllergy) {
                Box(
                    modifier = Modifier
                        .background(Colors.Danger.color10, RoundedCornerShape(8.dp))
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        "Alergi Dideteksi",
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
                modifier = Modifier,
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(gridItems.size) { item ->
                    NutrientProgressBar(
                        label = gridItems[item].first,
                        current = gridItems[item].second.first?.toInt() ?: 1,
//                        current = 10,
                        goal = gridItems[item].second.second?.toInt() ?: 1,
                        imageResource = R.drawable.calories
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
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
            onClick = {
                uiState.foodInfo?.let {
                    onMealSubmit()
                }
            },
            text = "Tambahkan Aktivitas",
            modifier = Modifier.fillMaxWidth()
        )
    }
}