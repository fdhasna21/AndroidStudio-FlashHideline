package com.fdhasna21.flashhideline.feature.webview

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.fdhasna21.flashhideline.core.base.BaseContent
import com.fdhasna21.flashhideline.core.base.BaseScreen
import com.fdhasna21.flashhideline.core.base.BaseViewModel.UiState
import com.fdhasna21.flashhideline.core.utils.Constants

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun WebViewScreen(
    url: String,
    viewModel: WebViewViewModel,
    onBackClick: () -> Unit = {}
) {
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var webProgress by remember { mutableIntStateOf(0) }

    LaunchedEffect(url) {
        viewModel.setUrl(url)
    }

    val handleBackNavigation = {
        val webView = webViewRef
        val history = webView?.copyBackForwardList()
        val previousUrl = history?.takeIf { it.currentIndex > 0 }
            ?.let { it.getItemAtIndex(it.currentIndex - 1)?.url }

        if (webView != null && webView.canGoBack() && !previousUrl.isNullOrEmpty() && previousUrl != "about:blank") {
            webView.goBack()
        } else {
            onBackClick()
        }
    }

    BackHandler(enabled = true) {
        handleBackNavigation()
    }

    BaseScreen(
        viewModel = viewModel,
        showBackButton = true,
        onBackClick = { handleBackNavigation() }
    ) { data ->
        WebViewContent(
            url = data ?: "",
            onWebViewCreated = { webViewRef = it }
        )
    }
}

@Composable
fun WebViewContent(
    url: String,
    onWebViewCreated: (WebView) -> Unit = {},
    onLoadingProgressChanged: (Int) -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "WebView Preview\nURL: $url",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            onLoadingProgressChanged(newProgress)
                        }
                    }
                    settings.javaScriptEnabled = true
                    onWebViewCreated(this)
                    if (url.isNotBlank()) {
                        loadUrl(url)
                    }
                }
            },
            update = { webView ->
                if (webView.url != url && url.isNotEmpty()) {
                    webView.loadUrl(url)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@ThemePreviews
@Composable
private fun WebViewScreenPreview() {
    FlashHidelineTheme {
        BaseContent<String>(
            uiState = UiState.Success(Constants.DEFAULT.URL),
            showBackButton = true
        ) { url ->
            WebViewContent(url = url ?: "")
        }
    }
}