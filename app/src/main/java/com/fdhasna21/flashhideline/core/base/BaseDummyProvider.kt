package com.fdhasna21.flashhideline.core.base

import android.content.Context
import androidx.annotation.RawRes
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fdhasna21.flashhideline.data.model.response.GetEverythingResponse
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import java.io.IOException

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

abstract class BaseDummyProvider<T, R>(
    protected val context: Context,
    protected val objectMapper: ObjectMapper
) {
    abstract fun getSingleData(): T
    abstract fun getListData(): List<T>
    abstract fun getFullResponse(): R?

    protected inline fun <reified DATA> loadFromRaw(@RawRes rawResId: Int): DATA? {
        return try {
            val inputStream = context.resources.openRawResource(rawResId)
            objectMapper.readValue<DATA>(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}