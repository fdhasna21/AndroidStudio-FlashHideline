package com.fdhasna21.flashhideline.core.base

import com.fdhasna21.flashhideline.core.network.NetworkResult
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.network.NetworkStateInfo
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.core.utils.ext.logE
import com.fdhasna21.flashhideline.core.utils.ext.logI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * **/

abstract class BaseRepository(
    private val networkStateInfo: NetworkStateInfo
) {
    protected suspend fun <T> safeApiCall(
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
                        message =  response.message().ifEmpty { "Error ${response.code()}" }.asUiText(),
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
}