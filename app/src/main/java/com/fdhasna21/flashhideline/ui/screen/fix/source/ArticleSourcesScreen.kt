package com.fdhasna21.flashhideline.ui.screen.fix.source

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.ui.component.CustomSearchBarWithFilter
import com.fdhasna21.flashhideline.ui.screen.fix.ArticleCategory
import androidx.compose.runtime.getValue
import com.fdhasna21.flashhideline.ui.screen.references.sources.SourcesItem
import androidx.compose.foundation.lazy.grid.items

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun ArticleSourcesScreen(
    category: ArticleCategory,
    viewModel: ArticleSourceViewModel,
    onBackClick: () -> Unit,
    onSourceSelected: (SourceItem) -> Unit,
) {
    val sources by viewModel.sources.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    BaseScreen(
        viewModel = viewModel,
        showBackButton = true,
        onBackClick = onBackClick
    ) {
        ArticleSourcesContent(
            category = category,
            sources = sources,
            onSourceSelected = onSourceSelected,
            searchQuery = searchQuery,
            onSearchQueryChange = viewModel::onSearchQueryChange
        )
    }
}

@Composable
fun ArticleSourcesContent(
    category: ArticleCategory,
    sources: List<SourceItem> = emptyList(),
    onSourceSelected: (SourceItem) -> Unit,
    searchQuery: String = "",
    onSearchQueryChange: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.sources_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.source_desc, category.name.lowercase()),
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
            isDebounceSearch = true,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (sources.isEmpty()) {
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(
                    items = sources,
                    key = { it.id }
                ) { source ->
                    SourcesItem(
                        source = source,
                        onClick = { onSourceSelected(source) }
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ArticleSourcesContentPreview() {
    FlashHidelineTheme {
        BaseContent(
            showBackButton = true,
            onBackClick = {}
        ) {
            ArticleSourcesContent(
                category = ArticleCategory.BUSINESS,
                onSourceSelected = {}
            )
        }
    }
}