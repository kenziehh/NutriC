package com.lalapanbulaos.nutric.features.article.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.lalapanbulaos.nutric.core.models.Article
import com.lalapanbulaos.nutric.core.utils.formatDateArticle1
import com.lalapanbulaos.nutric.core.utils.formatDateArticle2
import com.lalapanbulaos.nutric.presentation.theme.Colors
import com.lalapanbulaos.nutric.presentation.theme.NutriCTypography

@Composable
fun ArticleCard(article: Article, isHorizontal: Boolean, onArticleClick: () -> Unit) {
    val imageHeight = if (isHorizontal) 120.dp else 180.dp
    val cardModifier = if (isHorizontal)
        Modifier
            .width(200.dp)
            .padding(end = 8.dp)
    else
        Modifier.fillMaxWidth()

    Card(
        modifier = cardModifier.padding(start = 1.dp, bottom = 8.dp).clickable {
            onArticleClick()
        },
        elevation = 1.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column (
            Modifier.height(200.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.imageUrl)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 8.dp,
                    bottom = 12.dp
                )
            ) {
                Text(
                    text = article.title,
                    style = NutriCTypography.subHeadingSm,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))

                Row {
                    Text(
                        text = formatDateArticle1(article.createdAt),
                        style = NutriCTypography.bodyXs,
                        color = Colors.Neutral.color30
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = article.author,
                        style = NutriCTypography.bodyXs,
                        color = Colors.Neutral.color30
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleCardVertical(article: Article, onArticleClick: () -> Unit) {
    Card(
        elevation = 1.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(bottom = 8.dp).clickable {
            onArticleClick()
        }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(article.imageUrl).build(),
                contentDescription = "Article Image",
                modifier = Modifier
                    .width(120.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = article.title,
                    style = NutriCTypography.subHeadingLg,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Ditulis oleh ${article.author}",
                    style = NutriCTypography.bodySm,
                    maxLines = 1,
                    color = Colors.Neutral.color30
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = formatDateArticle2(article.createdAt),
                    style = NutriCTypography.bodyXs,
                    color = Colors.Secondary.color40
                )
            }
        }
    }
}