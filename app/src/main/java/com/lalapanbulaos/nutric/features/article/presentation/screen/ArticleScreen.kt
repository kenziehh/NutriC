package com.lalapanbulaos.nutric.features.article.presentation.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.annotations.SerializedName
import com.lalapanbulaos.nutric.core.models.Article
import com.lalapanbulaos.nutric.core.navigation.AppRoutes
import com.lalapanbulaos.nutric.features.article.presentation.component.ArticleCard
import com.lalapanbulaos.nutric.features.article.presentation.component.ArticleCardVertical
import com.lalapanbulaos.nutric.features.article.presentation.viewmodel.ArticleViewModel
import com.lalapanbulaos.nutric.presentation.component.NutriCTextField
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun ArticleScreen(
    navController: NavController,
    viewModel: ArticleViewModel = hiltViewModel()
) {
    val articleList = viewModel.uiState.collectAsState().value.articles
    val inputState = viewModel.inputState.collectAsState().value
    val isLoading = viewModel.uiState.collectAsState().value.isLoading

    fun handleArticleClick(article: Article) {
        viewModel.onSelectSingleArticle(article)
        navController.navigate(AppRoutes.ArticleDetail.createRoute(article.id))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 108.dp)
            .safeDrawingPadding()
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Artikel",
                    style = NutriCTypography.headingMd,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            NutriCTextField(
                value = inputState.searchQuery,
                onValueChange = {
                    viewModel.updateSearchQuery(it)
                },
                label = "Cari Artikel",
                trailingIcon = {
                    if (inputState.searchQuery.isNotBlank()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            modifier = Modifier.clickable {
                                viewModel.updateSearchQuery("")
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.clickable {
                                // Implement any action here, if needed
                            }
                        )
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            Text(
                text = "Rekomendasi Artikel",
                style = NutriCTypography.subHeadingMd,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        color = Colors.Primary.color40,
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentHeight()
                            .align(Alignment.Center)
                    )
                }
            }
        } else if (articleList.isNotEmpty()) {

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    items(articleList.size) { i ->
                        ArticleCard(
                            article = articleList[i],
                            isHorizontal = true,
                            onArticleClick = { handleArticleClick(articleList[i]) }
                        )
                    }
                }
            }

            items(articleList.size) { i ->
                ArticleCardVertical(article = articleList[i], onArticleClick = { handleArticleClick(articleList[i]) })
            }
        } else {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Tidak ada artikel yang ditemukan",
                        style = NutriCTypography.bodyMd,
                        textAlign = TextAlign.Center,
                        color = Colors.Neutral.color30,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}