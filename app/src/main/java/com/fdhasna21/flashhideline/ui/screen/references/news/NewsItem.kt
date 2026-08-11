package com.fdhasna21.flashhideline.ui.screen.references.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fdhasna21.flashhideline.core.theme.AccentElectricAmber
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.core.utils.ext.toRelativeTime
import com.fdhasna21.flashhideline.core.utils.ext.toRelativeTimeString
import com.fdhasna21.flashhideline.data.dummy.HeadlinesDummyProvider
import com.fdhasna21.flashhideline.data.model.item.ArticleItem

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun NewsItem(
    article: ArticleItem,
    onClick: (article: ArticleItem) -> Unit
){
    val context = LocalContext.current
    val authorName = article.author.ifBlank { article.source.name }
    val relativeTime = article.publishedAt.toRelativeTime()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(article) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(2.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (authorName.isNotBlank()) {
                    Text(
                        text = authorName,
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentElectricAmber,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                }

                if (relativeTime.isNotBlank()) {
                    Text(
                        text =  " • " + article.publishedAt.toRelativeTimeString(context),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun NewsItemPreview() {
    val context = LocalContext.current
    val objectMapper = ObjectMapper().apply { registerKotlinModule() }
    val dummyProvider = HeadlinesDummyProvider(context, objectMapper)

    FlashHidelineTheme {
        NewsItem(dummyProvider.getSingleData()) { }
    }
}