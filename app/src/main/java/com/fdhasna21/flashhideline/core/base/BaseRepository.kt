package com.fdhasna21.flashhideline.core.base

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fdhasna21.flashhideline.core.network.NetworkResult
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.network.NetworkStateInfo
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.core.utils.ext.logE
import com.fdhasna21.flashhideline.core.utils.ext.logI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

abstract class BaseRepository(
    private val networkStateInfo: NetworkStateInfo,
    private val objectMapper: ObjectMapper
) {

    protected suspend fun <REQ : Any, T> apiWithQuery(
        request: REQ,
        apiCall: suspend (Map<String, String>) -> Response<T>
    ): NetworkResult<T> {
        val queryMap = request.toQueryMap()
        return api { apiCall(queryMap) }
    }

    protected suspend fun <T> api(
        apiCall: suspend () -> retrofit2.Response<T>
    ): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error(
                        message =  response.message().ifEmpty { response.getErrorMessage() }.asUiText(),
                        code = response.code()
                    )
                }
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        logE("HttpException")
                        NetworkResult.Error(R.string.server_down.asUiText())
                    }
                    else -> {
                        val isConnected: Boolean = networkStateInfo.isConnected()
                        if (isConnected) {
                            logI("Internet available")
                            NetworkResult.Error(R.string.cannot_connect_toserver.asUiText())
                        } else {
                            logI("Internet not available")
                            NetworkResult.Error(R.string.no_internet_connection.asUiText())
                        }
                    }
                }
            }
        }
    }

    fun Any.toQueryMap(): Map<String, String> {
        val map: Map<String, Any?> = objectMapper.convertValue(
            this,
            object : TypeReference<Map<String, Any?>>() {}
        )

        return map
            .filterValues { value ->
                when (value) {
                    null -> false
                    is String -> value.isNotBlank()
                    else -> true
                }
            } // skip null param = not send
            .mapValues { it.value.toString() }
    }

    fun <T> Response<T>.getErrorMessage(): String {
        return try {
            val errorJson = errorBody()?.string()
            if (!errorJson.isNullOrEmpty()) {
                val jsonObject = JSONObject(errorJson)
                val bodyMessage = jsonObject.optString("message").ifEmpty { null }
                bodyMessage?.let { "${code()} : $it" }
            } else null
        } catch (e: Exception) {
            null
        } ?: message().ifEmpty { "Error ${code()}" }
    }
}