package com.fdhasna21.flashhideline.ui.screen.references.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fdhasna21.flashhideline.core.base.BaseContent
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews
import com.fdhasna21.flashhideline.ui.screen.references.main.MainContent

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun SettingsScreen() {
    SettingsContent()
}

@Composable
fun SettingsContent(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Halaman Settings Screen",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@ThemePreviews
@Composable
fun SettingsScreenPreview() {
    FlashHidelineTheme {
        BaseContent(showBackButton = false) {
            MainContent { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    SettingsScreen()
                }
            }
        }
    }
}