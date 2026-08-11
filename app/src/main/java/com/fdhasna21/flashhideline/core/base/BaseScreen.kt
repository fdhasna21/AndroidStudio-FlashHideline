package com.fdhasna21.flashhideline.core.base

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fdhasna21.flashhideline.core.base.BaseViewModel.UiState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.theme.FlashHidelineTheme
import com.fdhasna21.flashhideline.core.utils.component.ThemePreviews


/**
 * Created by Fernanda Hasna on 10/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    content: @Composable (data: Any?) -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseContent(
        uiState = uiState,
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        content = content
    )

    /**
     *  UI Effect (one-time event: Toast, Snackbar, Dialog)
     *  */
    var dialogEffectState by remember { mutableStateOf<BaseViewModel.UiEffect.ShowDialog?>(null) }
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is BaseViewModel.UiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message.asString(context), Toast.LENGTH_SHORT).show()
                }
                is BaseViewModel.UiEffect.ShowDialog -> {
                    dialogEffectState = effect
                }
            }
        }
    }

    dialogEffectState?.let { dialog ->
        AlertDialog(
            onDismissRequest = { dialogEffectState = null },
            title = { Text(text = dialog.title.asString(context)) },
            text = { Text(text = dialog.message.asString(context)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogEffectState = null
                        dialog.onPositiveClick?.invoke()
                    }
                ) {
                    Text(text = dialog.positiveButtonText.asString(context))
                }
            }
        )
    }
}

/** Used for previewing UI **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseContent(
    uiState: UiState<Any?> = UiState.Idle,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    content: @Composable (data: Any?) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo_horizontal),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(120.dp)
                    )
                },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val successData = (uiState as? UiState.Success)?.data
            content(successData)

            /**
             *  UI State (persistent when screen config change: rotation)
             *  */
            if (uiState is UiState.Loading) {
                Dialog(
                    onDismissRequest = {},
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    )
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White
                    ) {
                        Box(
                            modifier = Modifier.padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun BaseContentSuccessPreview() {
    FlashHidelineTheme {
        BaseContent(
            uiState = UiState.Success("Data Dummy"),
            showBackButton = true
        ) { data ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Konten Screen: $data")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun BaseContentLoadingPreview() {
    FlashHidelineTheme {
        BaseContent(
            uiState = UiState.Loading,
            showBackButton = true
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Konten Utama Tertutup Loading")
            }
        }
    }
}
