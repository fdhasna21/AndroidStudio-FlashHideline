package com.fdhasna21.flashhideline.ui.screen.fix

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector
import com.fdhasna21.flashhideline.R

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

enum class ArticleCategory(
    val key: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    ALL("all", R.string.category_all, Icons.Outlined.Public),
    BUSINESS("business", R.string.category_business, Icons.Outlined.BusinessCenter),
    ENTERTAINMENT("entertainment", R.string.category_entertainment, Icons.Outlined.Movie),
    GENERAL("general", R.string.category_general, Icons.Outlined.Newspaper),
    HEALTH("health", R.string.category_health, Icons.Outlined.HealthAndSafety),
    SCIENCE("science", R.string.category_science, Icons.Outlined.Science),
    SPORTS("sports", R.string.category_sports, Icons.Outlined.SportsSoccer),
    TECHNOLOGY("technology", R.string.category_technology, Icons.Outlined.Memory)
}