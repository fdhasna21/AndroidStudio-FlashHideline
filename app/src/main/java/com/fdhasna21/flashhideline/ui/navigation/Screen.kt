package com.fdhasna21.flashhideline.ui.navigation

import android.net.Uri
import com.fdhasna21.flashhideline.core.utils.Constants
import com.fdhasna21.flashhideline.core.utils.component.objectMapper
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object WebView : Screen("webview_screen/{${Constants.EXTRA.ENCODED_URL}}") {
        fun createRoute(url: String): String {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            return "webview_screen/$encodedUrl"
        }
    }

    object ArticleCategories : Screen("article_categories_screen")
    object ArticleSources : Screen("article_sources_screen/{${Constants.EXTRA.ARTICLE_CAT}}"){
        fun createRoute(articleCategory: String): String {
            return "article_sources_screen/$articleCategory"
        }
    }
    object Article : Screen("article_screen/{${Constants.EXTRA.SOURCE_ITEM}}") {
        fun createRoute(source: SourceItem): String {
            val json = objectMapper.writeValueAsString(source)
            val encodedJson = Uri.encode(json)
            return "article_screen/$encodedJson"
        }
    }
}