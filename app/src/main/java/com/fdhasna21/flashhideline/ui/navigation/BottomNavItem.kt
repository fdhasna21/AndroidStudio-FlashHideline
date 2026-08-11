package com.fdhasna21.flashhideline.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.fdhasna21.flashhideline.R

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

sealed class BottomNavItem(val route: String, @StringRes val titleRes: Int, val icon: ImageVector) {
    object News : BottomNavItem("news", R.string.news_title, Icons.Default.Home)
    object Sources : BottomNavItem("sources", R.string.sources_title, Icons.Default.AccountBox)
    object Settings : BottomNavItem("settings", R.string.settings_title, Icons.Default.Settings)
}