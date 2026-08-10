package com.fdhasna21.flashhideline.feature.webview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.fdhasna21.flashhideline.core.base.BaseActivity
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@AndroidEntryPoint
class WebViewActivity : BaseActivity<WebViewViewModel>() {

    private var targetUrl: String = ""

    @Composable
    override fun Content(viewModel: WebViewViewModel) {
        WebViewScreen(
            url = targetUrl,
            viewModel = viewModel,
            onBackClick = { finish() }
        )
    }

    override fun onSetupData() {
        targetUrl = intent.getStringExtra(Constants.EXTRA.STRING) ?: "https://google.com"
    }
}

