package com.fdhasna21.flashhideline.feature.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.base.BaseActivity
import com.fdhasna21.flashhideline.core.base.BaseContent
import com.fdhasna21.flashhideline.core.base.BaseScreen
import com.fdhasna21.flashhideline.core.base.BaseViewModel.UiState
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.Constants
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.feature.webview.WebViewContent

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onOpenWebView: (String) -> Unit
) {
    val context = LocalContext.current
    BaseScreen(
        viewModel = viewModel,
        showBackButton = false
    ) { data ->
        MainContent(onOpenWebView)
    }
}

@Composable
fun MainContent(
    onOpenWebView: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                onOpenWebView(Constants.DEFAULT.URL)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Button ke Google")
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}


@ThemePreviews
@Composable
fun MainScreenPreview() {
    FlashHidelineTheme {
        BaseContent<String>() {
            MainContent{}
        }
    }
}