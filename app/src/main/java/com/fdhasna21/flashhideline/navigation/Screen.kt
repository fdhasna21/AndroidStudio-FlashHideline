package com.fdhasna21.flashhideline.navigation

import com.fdhasna21.flashhideline.core.utils.Constants
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

sealed class Screen(val route: String) {
    object Main : Screen("main_screen")
    object WebView : Screen("webview_screen/{${Constants.EXTRA.ENCODED_URL}}") {
        fun createRoute(url: String): String {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            return "webview_screen/$encodedUrl"
        }
    }
}