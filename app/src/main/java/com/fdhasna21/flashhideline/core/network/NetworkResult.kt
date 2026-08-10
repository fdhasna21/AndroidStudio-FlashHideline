package com.fdhasna21.flashhideline.core.network

import com.fdhasna21.flashhideline.core.utils.component.UiText

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val message: UiText, val code: Int? = null) : NetworkResult<Nothing>
}