package com.lalapanbulaos.nutric.features.auth.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.SignInViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignInScreen(viewModel: SignInViewModel = hiltViewModel()) {
  val inputState by viewModel.inputState.collectAsState()


  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TextField(
      value = inputState.username,
      onValueChange = {viewModel.onUsernameChanged(it)},
      label = { Text("Username") },
      modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      value = inputState.password,
      onValueChange = { viewModel.onPasswordChanged(it) },
      label = { Text("Password") },
      modifier = Modifier.fillMaxWidth(),
      visualTransformation = PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = {
        viewModel.signIn()
      },
      modifier = Modifier.fillMaxWidth()
    ) {
      Text("Sign In")
    }


  }
}