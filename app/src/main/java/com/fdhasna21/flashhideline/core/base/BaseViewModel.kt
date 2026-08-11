package com.fdhasna21.flashhideline.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.network.NetworkResult
import com.fdhasna21.flashhideline.core.utils.component.UiText
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

abstract class BaseViewModel : ViewModel() {
    /**
     *  UI State (persistent when screen config change: rotation)
     *  */
    sealed interface UiState<out T> {
        object Idle : UiState<Nothing>
        object Loading : UiState<Nothing>
        data class Success<T>(val data: T) : UiState<T>
        data class Error(val message: UiText) : UiState<Nothing>
    }

    private val _uiState = MutableStateFlow<UiState<Any?>>(UiState.Idle)
    val uiState: StateFlow<UiState<Any?>> = _uiState.asStateFlow()

    protected fun updateState(state: UiState<Any?>) {
        _uiState.value = state
    }

    protected fun <R> launchNetwork(
        call: suspend () -> NetworkResult<R>,
        onSuccess: (R) -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val result = call()) {
                is NetworkResult.Success -> {
                    _uiState.value = UiState.Success(result.data)
                    onSuccess.invoke(result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = UiState.Error(result.message)
                    sendEffect(UiEffect.ShowDialog(message = result.message))
                }
            }
        }
    }

    /**
     *  UI Effect (one-time event: Toast, Snackbar, Dialog)
     *  */
    sealed interface UiEffect {
        data class ShowToast(val message: UiText) : UiEffect
        data class ShowDialog(
            val title: UiText = R.string.warning.asUiText(),
            val message: UiText,
            val positiveButtonText: UiText = R.string.ok.asUiText(),
            val onPositiveClick: (() -> Unit)? = null
        ) : UiEffect
    }

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect: SharedFlow<UiEffect> = _uiEffect.asSharedFlow()

    protected fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}