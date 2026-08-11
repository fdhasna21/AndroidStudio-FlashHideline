package com.fdhasna21.flashhideline.ui.screen.fix.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.base.BaseContent
import com.fdhasna21.flashhideline.core.base.BaseScreen
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.ui.component.CustomSearchBarWithFilter
import com.fdhasna21.flashhideline.ui.screen.references.news.NewsItem
import kotlin.text.ifEmpty
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import com.fdhasna21.flashhideline.core.utils.component.OnBottomReached

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun ArticleScreen(
    source: SourceItem,
    viewModel: ArticleViewModel,
    onBackClick: () -> Unit,
    onArticleSelected: (ArticleItem) -> Unit,
) {
    val articles by viewModel.articles.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    BaseScreen(
        viewModel = viewModel,
        showBackButton = true,
        onBackClick = onBackClick
    ) {
        ArticleContent(
            source = source,
            articles = articles,
            isInlineLoading = viewModel.isInlineLoading,
            onLoadNextPage = viewModel::loadNextPage,
            onArticleSelected = onArticleSelected,
            searchQuery = searchQuery,
            onSearchQueryChange = viewModel::onSearchQueryChange,
        )
    }
}

@Composable
fun ArticleContent(
    source: SourceItem,
    articles: List<ArticleItem> = emptyList(),
    isInlineLoading: Boolean = false,
    onLoadNextPage: () -> Unit = {},
    onArticleSelected: (ArticleItem) -> Unit,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {}
) {
    val listState = rememberLazyListState()
    listState.OnBottomReached(buffer = 2) {
        onLoadNextPage()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isInlineLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Text(
            text = stringResource(id = R.string.news_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.news_desc, source.name),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomSearchBarWithFilter(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            isFilterVisible = false,
            isDebounceSearch = false,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (articles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_data_found),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(
                    items = articles,
                    key = { article -> article.url.ifEmpty { article.title } }
                ) { article ->
                    NewsItem(
                        article = article,
                        onClick = { onArticleSelected(article) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ArticleContentPreview() {
    FlashHidelineTheme {
        BaseContent(
            showBackButton = true,
            onBackClick = {}
        ) {
            ArticleContent(
                source = SourceItem(),
                onArticleSelected = {}
            )
        }
    }
}