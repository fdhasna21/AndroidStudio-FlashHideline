package com.fdhasna21.flashhideline.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fdhasna21.flashhideline.core.base.BaseScreen

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

@Composable
fun MainScreen(viewModel: MainViewModel) {
    BaseScreen(viewModel = viewModel) { data ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data ?: "Empty",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}