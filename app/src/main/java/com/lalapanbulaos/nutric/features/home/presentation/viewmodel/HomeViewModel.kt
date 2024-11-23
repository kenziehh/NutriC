package com.lalapanbulaos.nutric.features.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalapanbulaos.nutric.core.data.local.pref.UserPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _userName = MutableStateFlow("Guest")
    val userName: StateFlow<String> get() = _userName

    init {
        viewModelScope.launch {
            userPreferencesManager.user.collect { user ->
                _userName.value = user?.username ?: "Guest"
            }
        }
    }
}
