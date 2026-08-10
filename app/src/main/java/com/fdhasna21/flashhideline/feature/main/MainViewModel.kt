package com.fdhasna21.flashhideline.feature.main

import com.fdhasna21.flashhideline.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<String>() {
    init {
        updateState(UiState.Success("Hello"))
    }
}