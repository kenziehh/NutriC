package com.lalapanbulaos.nutric.features.article.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.lalapanbulaos.nutric.R
import com.lalapanbulaos.nutric.core.models.Article
import com.lalapanbulaos.nutric.core.utils.formatDateArticle2
import com.lalapanbulaos.nutric.features.article.presentation.viewmodel.ArticleViewModel
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun ArticleDetailScreen(onGoBack: () -> Unit = {}, articleId: String?, viewModel: ArticleViewModel = hiltViewModel()) {
    Log.d("ArticleDetailScreen", "ArticleId: $articleId")
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = articleId) {
        if (articleId != null) {
            viewModel.fetchSingleArticle(articleId)
        }
    }

    val selectedArticle = uiState.selectedArticle

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        item {

            Row(
                modifier = Modifier
                    .padding(vertical = 18.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onGoBack()
                        }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kembali", style = NutriCTypography.subHeadingSm)
            }
        }

        item {
            AsyncImage(
                model = selectedArticle?.imageUrl,
                contentDescription = "Article",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f / 4f)
                        .background(Colors.Neutral.color30),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    selectedArticle?.title ?: "Title",
                    style = NutriCTypography.subHeadingMd
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    Text(
                        "Ditulis oleh ${selectedArticle?.author ?: "Author"}",
                        style = NutriCTypography.bodySm,
                        color = Color(0XFF848484)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            formatDateArticle2(selectedArticle?.updatedAt),
                            style = NutriCTypography.bodyXs,
                            color = Colors.Secondary.color30
                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Box(
//                            modifier = Modifier
//                                .size(2.dp)
//                                .background(color = Colors.Neutral.color30, shape = CircleShape)
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            "17:09",
//                            style = NutriCTypography.bodyXs,
//                            color = Colors.Secondary.color30
//                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = selectedArticle?.content ?: "Content",
                        style = NutriCTypography.bodySm,
                        color = Color(0XFF848484)
                    )
                }
            }

        }
    }

}
