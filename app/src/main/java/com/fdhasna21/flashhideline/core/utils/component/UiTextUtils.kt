package com.fdhasna21.flashhideline.core.utils.component

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

sealed interface UiText {
    data class PlainString(val value: String) : UiText

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is PlainString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is PlainString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}

fun String.asUiText(): UiText = UiText.PlainString(this)

fun @receiver:StringRes Int.asUiText(vararg args: Any): UiText = UiText.StringResource(this, *args)