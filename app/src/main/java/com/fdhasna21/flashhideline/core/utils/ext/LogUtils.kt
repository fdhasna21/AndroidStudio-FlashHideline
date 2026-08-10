package com.fdhasna21.flashhideline.core.utils.ext

import android.util.Log

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

val Any.TAG: String
    get() = this::class.java.simpleName

fun Any.logD(message: String) {
    Log.d(this.TAG, message)
}

fun Any.logI(message: String) {
    Log.i(this.TAG, message)
}

fun Any.logE(message: String, throwable: Throwable? = null) {
    Log.e(this.TAG, message, throwable)
}