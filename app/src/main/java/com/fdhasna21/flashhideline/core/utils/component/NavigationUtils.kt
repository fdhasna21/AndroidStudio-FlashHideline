package com.fdhasna21.flashhideline.core.utils.component

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * Created by Fernanda Hasna on 12/08/2026.
 * **/

val objectMapper: ObjectMapper = jacksonObjectMapper()

inline fun <reified T : Parcelable> parcelableNavType(
    isNullableAllowed: Boolean = true
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {

    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): T {
        return objectMapper.readValue(value, T::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }
}