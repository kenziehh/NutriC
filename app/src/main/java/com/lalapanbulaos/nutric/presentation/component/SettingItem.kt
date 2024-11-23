package com.lalapanbulaos.nutric.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.features.profile.data.Setting
import com.lalapanbulaos.nutric.features.profile.data.SettingType
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun SettingItem(setting: Setting) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp).background(Colors.Neutral.color00)
            .border(color = Colors.Border.white, width = 1.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.padding(8.dp).background(if (setting.type == SettingType.SUCCESS) Colors.Primary.color05 else Colors.Danger.color10
                ,shape = RoundedCornerShape(8.dp)
            ).size(40.dp)
            ) {
                Image(
                    painter = painterResource(id = setting.icon),
                    contentDescription = setting.title,
                    modifier = Modifier.size(24.dp).align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = setting.title,
                style = NutriCTypography.bodyMd,
                color = Colors.Neutral.color50
            )
        }
        Image(painter = painterResource(R.drawable.arrow_right), contentDescription = setting.title, modifier = Modifier.size(24.dp))
    }
}
