package com.fdhasna21.flashhideline.ui.screen.references.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fdhasna21.flashhideline.core.base.BaseContent
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.ui.screen.references.main.MainContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.ui.component.CustomSearchBarWithFilter
import androidx.compose.runtime.collectAsState
import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.data.model.response.GetEverythingResponse
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onArticleClick: (ArticleItem) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Ambil data success
    val response = (uiState as? BaseViewModel.UiState.Success<*>)?.data as? GetEverythingResponse
    val articles = response?.articles ?: emptyList()


    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isFilterOpen by rememberSaveable { mutableStateOf(false) }

    NewsContent(
        articles = articles,
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        onSearch = { query ->

        },
        isFilterExpanded = isFilterOpen,
        onFilterToggle = { isFilterOpen = !isFilterOpen },
        onArticleClick = onArticleClick,
        filterContent = {
            Text(
                text = "Filter Options Content",
                style = MaterialTheme.typography.bodySmall
            )
        }
    )
}

@Composable
fun NewsContent(
    articles: List<ArticleItem> = emptyList(),
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    isFilterExpanded: Boolean = false,
    onFilterToggle: () -> Unit = {},
    onArticleClick: (ArticleItem) -> Unit = {},
    filterContent: @Composable () -> Unit = {}
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
            text = stringResource(id = R.string.news_desc),
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
            isFilterExpanded = isFilterExpanded,
            onFilterToggle = onFilterToggle,
            isDebounceSearch = false,
            filterContent = filterContent,
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
                        onClick = { onArticleClick(article) }
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
fun NewsScreenPreview() {
    FlashHidelineTheme {
        BaseContent(showBackButton = false) {
            MainContent { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NewsContent()
                }
            }
        }
    }
}