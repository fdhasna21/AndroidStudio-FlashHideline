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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.data.model.response.GetEverythingResponse
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import com.fdhasna21.flashhideline.ui.component.CustomSearchBarWithFilter
import com.fdhasna21.flashhideline.ui.screen.fix.ArticleCategory
import com.fdhasna21.flashhideline.ui.screen.fix.source.ArticleSourceViewModel
import com.fdhasna21.flashhideline.ui.screen.references.news.NewsItem
import kotlin.text.ifEmpty

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
    val uiState by viewModel.uiState.collectAsState()
    val response = (uiState as? BaseViewModel.UiState.Success<*>)?.data as? GetEverythingResponse
    val articles = response?.articles ?: emptyList()
    var searchQuery by rememberSaveable { mutableStateOf("") }

    BaseScreen(
        viewModel = viewModel,
        showBackButton = true,
        onBackClick = onBackClick
    ) {
        ArticleContent(
            source = source,
            articles = articles,
            onArticleSelected = onArticleSelected,
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            onSearch = { query ->
                // ViewModel action jika butuh manual search/trigger
            }
        )
    }
}

@Composable
fun ArticleContent(
    source: SourceItem,
    articles: List<ArticleItem> = emptyList(),
    onArticleSelected: (ArticleItem) -> Unit,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
            onSearch = onSearch,
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