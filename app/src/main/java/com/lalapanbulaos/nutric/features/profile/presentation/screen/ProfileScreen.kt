package com.lalapanbulaos.nutric.features.profile.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.features.auth.presentation.viewmodel.AuthViewModel
import com.lalapanbulaos.nutric.features.profile.data.Setting
import com.lalapanbulaos.nutric.features.profile.data.SettingType
import com.lalapanbulaos.nutric.features.profile.presentation.viewmodel.ProfileViewModel
import com.lalapanbulaos.nutric.presentation.component.SettingItem
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking


@Composable
fun ProfileScreen(navController: NavController,onLogout:()->Unit,authViewModel: AuthViewModel = hiltViewModel()){
    val viewModel: ProfileViewModel = hiltViewModel()
    val userNameState = viewModel.userName.collectAsState(initial = "Guest")
    val userName = userNameState.value
    val menuItems = listOf(
        Setting("Pusat Bantuan & FAQ", R.drawable.faq, SettingType.SUCCESS),
        Setting("Kebijakan & Privasi", R.drawable.terms,SettingType.SUCCESS),
        Setting("Tentang Aplikasi", R.drawable.about,SettingType.SUCCESS),
        Setting("Rating Aplikasi", R.drawable.rating,SettingType.SUCCESS),
    )
    val context = LocalContext.current
    val settingItems = listOf(
        Setting("Hapus Akun", R.drawable.delete,SettingType.DANGER),
        Setting("Keluar", R.drawable.logout,SettingType.SUCCESS,
            {
                runBlocking {
                    authViewModel.logout()
                }
                onLogout()
            })
    )

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.photo_profile),
                    contentDescription = "Profil",
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(userName, style = NutriCTypography.subHeadingMd)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Jakarta, Indonesia", style = NutriCTypography.bodySm)
                }
            }
            Text("Edit Profil", style = NutriCTypography.subHeadingSm, color = Colors.Primary.color50)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Seputar Aplikasi", style = NutriCTypography.subHeadingMd)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.height(250.dp)) {
            items(menuItems) { menuItem ->
                SettingItem(setting = menuItem)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Pengaturan", style = NutriCTypography.subHeadingMd)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.height(120.dp)) {
            items(settingItems) { settingItem ->
                SettingItem(setting = settingItem)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

//        Button(onClick = {
//            runBlocking {
//                authViewModel.logout()
//            }
//            onLogout()
//        }) {
//            Text("Logout")
//        }

    }
}