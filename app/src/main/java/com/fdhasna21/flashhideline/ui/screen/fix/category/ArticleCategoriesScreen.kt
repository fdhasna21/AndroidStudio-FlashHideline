package com.fdhasna21.flashhideline.ui.screen.fix.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.fdhasna21.flashhideline.ui.screen.fix.ArticleCategory
import com.fdhasna21.flashhideline.ui.screen.references.main.MainViewModel

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun ArticleCategoriesScreen(
    viewModel: MainViewModel,
    onCategorySelected: (ArticleCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    BaseScreen(
        viewModel = viewModel,
        showBackButton = false
    ) {
        ArticleCategoriesContent(
            onCategorySelected = onCategorySelected,
            modifier = modifier
        )
    }
}

@Composable
fun ArticleCategoriesContent(
    onCategorySelected: (ArticleCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.select_category_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = ArticleCategory.entries,
                    key = { it.key }
                ) { category ->
                    ArticleCategoryItem(
                        articleCategory = category,
                        onClick = onCategorySelected
                    )
                }
            }
        }

        Text(
            text = stringResource(id = R.string.credit_footer),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@ThemePreviews
@Composable
private fun ArticleCategoriesContentPreview() {
    FlashHidelineTheme {
        BaseContent(
            showBackButton = false
        ) {
            ArticleCategoriesContent(
                onCategorySelected = {}
            )
        }
    }
}