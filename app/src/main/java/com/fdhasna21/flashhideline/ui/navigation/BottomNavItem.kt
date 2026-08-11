package com.fdhasna21.flashhideline.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object News : BottomNavItem("news", "News", Icons.Default.Home)
    object Sources : BottomNavItem("sources", "Sources", Icons.Default.AccountBox)
    object Settings : BottomNavItem("settings", "Profile", Icons.Default.Settings)
}