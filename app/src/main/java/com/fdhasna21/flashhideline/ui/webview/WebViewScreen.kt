package com.fdhasna21.flashhideline.ui.webview

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.fdhasna21.flashhideline.core.base.BaseContent
import com.fdhasna21.flashhideline.core.base.BaseScreen
import com.fdhasna21.flashhideline.core.base.BaseViewModel.UiState
import com.fdhasna21.flashhideline.core.utils.Constants
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LinearProgressIndicator

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
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
        Column(modifier = Modifier.fillMaxSize()) {
            if (webProgress in 1..99) {
                LinearProgressIndicator(
                    progress = { webProgress / 100f },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            WebViewContent(
                url = (data as? String) ?: url,
                onWebViewCreated = { webViewRef = it },
                onLoadingProgressChanged = { newProgress ->
                    webProgress = newProgress
                }
            )
        }
    }
}

@Composable
fun WebViewContent(
    url: String,
    onWebViewCreated: (WebView) -> Unit = {},
    onLoadingProgressChanged: (Int) -> Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val isDark = isSystemInDarkTheme()
    var loadedUrl by rememberSaveable { mutableStateOf("") }

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
                            onLoadingProgressChanged(newProgress)
                        }
                    }
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                    }
                    onWebViewCreated(this)
                }
            },
            update = { webView ->
                if (url.isNotBlank() && url != loadedUrl) {
                    loadedUrl = url
                    webView.loadUrl(url)
                }

                // Update dynamic UI Mode without re-load URL (scroll & page same)
                if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
                    WebSettingsCompat.setAlgorithmicDarkeningAllowed(webView.settings, isDark)
                } else if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    val forceDarkOption = if (isDark) {
                        WebSettingsCompat.FORCE_DARK_ON
                    } else {
                        WebSettingsCompat.FORCE_DARK_OFF
                    }
                    WebSettingsCompat.setForceDark(webView.settings, forceDarkOption)
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
        BaseContent(
            uiState = UiState.Success(Constants.DEFAULT.URL),
            showBackButton = true
        ) { url ->
            WebViewContent(url = (url as? String) ?: "")
        }
    }
}