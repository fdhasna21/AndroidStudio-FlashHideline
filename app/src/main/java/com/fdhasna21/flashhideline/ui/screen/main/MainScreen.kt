package com.fdhasna21.flashhideline.ui.screen.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fdhasna21.flashhideline.core.base.BaseContent
import com.fdhasna21.flashhideline.core.base.BaseScreen
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.ui.navigation.BottomNavItem
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fdhasna21.flashhideline.core.theme.AccentElectricAmber
import com.fdhasna21.flashhideline.core.theme.AccentElectricAmberContainer
import com.fdhasna21.flashhideline.ui.screen.news.NewsScreen
import com.fdhasna21.flashhideline.ui.screen.settings.SettingsScreen
import com.fdhasna21.flashhideline.ui.screen.sources.SourcesScreen
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.ui.component.CustomBottomBar

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onArticleClick: (ArticleItem) -> Unit = {},
    onSourceClick: (SourceItem) -> Unit = {}
) {
    BaseScreen(
        viewModel = viewModel,
        showBackButton = false
    ) { data ->
        MainContent(
            onArticleClick = onArticleClick,
            onSourceClick = onSourceClick
        )
    }
}

@Composable
fun MainContent(
    onArticleClick: (ArticleItem) -> Unit = {},
    onSourceClick: (SourceItem) -> Unit = {},
    content: (@Composable (PaddingValues) -> Unit)? = null
) {
    val bottomNavController = rememberNavController()
    val items = listOf(
        BottomNavItem.News,
        BottomNavItem.Sources,
        BottomNavItem.Settings
    )
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                items = items,
                currentRoute = currentRoute,
                onItemClick = { item ->
                    bottomNavController.navigate(item.route) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.News.route,
            modifier = Modifier.padding(
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            composable(BottomNavItem.News.route) {
                NewsScreen(hiltViewModel(), onArticleClick = onArticleClick)
            }
            composable(BottomNavItem.Sources.route) {
                SourcesScreen(hiltViewModel())
            }
            composable(BottomNavItem.Settings.route) {
                SettingsScreen()
            }
        }
    }
}


@ThemePreviews
@Composable
fun MainScreenPreview() {
    FlashHidelineTheme {
        BaseContent() {
            MainContent()
        }
    }
}