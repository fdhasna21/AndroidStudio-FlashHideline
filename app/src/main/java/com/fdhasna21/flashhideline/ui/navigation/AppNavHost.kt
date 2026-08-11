package com.fdhasna21.flashhideline.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fdhasna21.flashhideline.core.utils.Constants
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.ui.screen.fix.category.ArticleCategoriesScreen
import com.fdhasna21.flashhideline.ui.screen.fix.ArticleCategory
import com.fdhasna21.flashhideline.ui.screen.fix.article.ArticleScreen
import com.fdhasna21.flashhideline.ui.screen.fix.source.ArticleSourcesScreen
import com.fdhasna21.flashhideline.ui.screen.references.main.MainScreen
import com.fdhasna21.flashhideline.ui.screen.webview.WebViewScreen

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Main.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                viewModel = hiltViewModel(),
                onArticleClick = { article ->
                    if (article.url.isNotBlank()) {
                        navController.navigate(Screen.WebView.createRoute(article.url))
                    }
                },
                onSourceClick = { source ->
                    if (source.url.isNotBlank()) {
                        navController.navigate(Screen.WebView.createRoute(source.url))
                    }
                }
            )
        }

        composable(
            route = Screen.WebView.route,
            arguments = listOf(
                navArgument(Constants.EXTRA.ENCODED_URL) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val rawUrl = backStackEntry.arguments?.getString(Constants.EXTRA.ENCODED_URL) ?: ""
            val targetUrl = Uri.decode(rawUrl).ifBlank { Constants.DEFAULT.URL }

            WebViewScreen(
                url = targetUrl,
                viewModel =  hiltViewModel(),
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.ArticleCategories.route
        ) {
            ArticleCategoriesScreen(
                viewModel = hiltViewModel(),
                onCategorySelected = { selectedCategory ->
                    navController.navigate(Screen.ArticleSources.createRoute(selectedCategory.key))
                }
            )
        }

        composable(
            route = Screen.ArticleSources.route
        ) { backStackEntry ->
            val key = backStackEntry.arguments?.getString(Constants.EXTRA.ARTICLE_CAT)
            val selectedCategory = ArticleCategory.entries.find { it.key == key } ?: ArticleCategory.ALL

            ArticleSourcesScreen(
                category = selectedCategory,
                viewModel = hiltViewModel(),
                onBackClick = { navController.popBackStack() },
                onSourceSelected = { selectedSource ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        Constants.EXTRA.SOURCE_ITEM,
                        selectedSource
                    )
                    navController.navigate(Screen.Article.route)
                }
            )
        }

        composable(
            route = Screen.Article.route
        ){ backStackEntry ->
            val selectedSource = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<SourceItem>(Constants.EXTRA.SOURCE_ITEM) ?: SourceItem()

            ArticleScreen(
                source = selectedSource,
                viewModel = hiltViewModel(),
                onBackClick = { navController.popBackStack() },
                onArticleSelected = { article ->
                    if (article.url.isNotBlank()) {
                        navController.navigate(Screen.WebView.createRoute(article.url))
                    }
                }
            )
        }
    }
}