package com.lalapanbulaos.nutric.features.auth.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.presentation.component.NutriCButton
import com.lalapanbulaos.nutric.presentation.component.NutriCTextField
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.CustomTypography

@Composable
fun AuthScreen(viewModel: AuthViewModel = hiltViewModel(), navController: NavController) {
    val inputState by viewModel.inputState.collectAsState()
    val authState by viewModel.authState.collectAsState()
    val isSignUpMode by viewModel.isSignUpMode.collectAsState()

    val context = LocalContext.current

    // Redirect to home if login is successful
    if (authState is AuthViewModel.AuthState.Success) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true } // Clear the backstack
            }
        }
    }

    // Show toast if an error occurs
    if (authState is AuthViewModel.AuthState.Error) {
        val errorMessage = (authState as AuthViewModel.AuthState.Error).errorMessage
        LaunchedEffect(errorMessage) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.asset_auth_topscreen), // Replace with your image resource
            contentDescription = "Sign In Image",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                if (isSignUpMode) "Daftar" else "Masuk",
                style = CustomTypography.headlineLarge,
                color = Colors.Primary.color40
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                if (isSignUpMode)
                    "Selamat datang di NutriC! Ayo daftarkan akunmu untuk pantau kebutuhan asupan harimu"
                else
                    "Halo, Selamat datang kembali di NutriC! Mari pantau kebutuhan nutrisimu",
                style = CustomTypography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            NutriCTextField(
                value = inputState.username,
                onValueChange = { viewModel.onUsernameChanged(it) },
                label = "Username",
                placeholder = "Masukkan username",
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(8.dp))

            NutriCTextField(
                value = inputState.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = "Password",
                placeholder = "Masukkan password",
                modifier = Modifier,
                isPassword = true
            )

            if (isSignUpMode) {
                Spacer(modifier = Modifier.height(8.dp))
                NutriCTextField(
                    value = inputState.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    label = "Confirm Password",
                    placeholder = "Masukkan konfirmasi password",
                    modifier = Modifier,
                    isPassword = true
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            AuthButton(
                isLoading = authState is AuthViewModel.AuthState.Loading,
                onClick = { viewModel.submit() },
                text = if (isSignUpMode) "Daftar" else "Masuk"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    color = Colors.Neutral.color30,
                    thickness = 1.dp
                )

                Text("Atau", color = Colors.Neutral.color30)

                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    color = Colors.Neutral.color30,
                    thickness = 1.dp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 10.dp),
                    tint = Color.Unspecified
                )
                Text("Masuk dengan Google", style = CustomTypography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 38.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Belum punya akun?", color = Colors.Neutral.color30,
                style = CustomTypography.bodyMedium
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                if (isSignUpMode) "Masuk" else "Daftar",
                modifier = Modifier.clickable(onClick = {
                    viewModel.toggleSignUpMode(!isSignUpMode)
                }),
                color = Colors.Primary.color40,
                style = CustomTypography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
fun AuthButton(isLoading: Boolean, onClick: () -> Unit, text: String = "Masuk") {
    NutriCButton(
        enabled = !isLoading,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        content = {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Colors.Primary.color10
                )
            } else {
                Text(text, style = CustomTypography.labelMedium)
            }
        }
    )
}



