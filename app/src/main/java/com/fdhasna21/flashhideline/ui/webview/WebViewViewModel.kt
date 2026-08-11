package com.fdhasna21.flashhideline.ui.webview

import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

@HiltViewModel
class WebViewViewModel @Inject constructor() : BaseViewModel() {
    fun setUrl(url: String) {
        if (url.isNotEmpty()) {
            updateState(UiState.Success(url))
        } else {
            updateState(UiState.Error(message="URL tidak valid".asUiText()))
        }
    }
}