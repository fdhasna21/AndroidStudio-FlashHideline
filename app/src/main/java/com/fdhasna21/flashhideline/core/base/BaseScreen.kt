package com.fdhasna21.flashhideline.core.base

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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


/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

@Composable
fun <T> BaseScreen(
    viewModel: BaseViewModel<out T>,
    content: @Composable (data: T?) -> Unit
) {
    val context = LocalContext.current

    /**
     *  UI State (persistent when screen config change: rotation)
     *  */
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        val successData = (uiState as? UiState.Success)?.data
        content(successData)

        if (uiState is UiState.Loading) {
            Dialog(
                onDismissRequest = {},
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
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
