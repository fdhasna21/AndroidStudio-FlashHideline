package com.fdhasna21.flashhideline.data.api

import com.fdhasna21.flashhideline.data.model.response.GetEverythingResponse
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import com.fdhasna21.flashhideline.data.model.response.GetSourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

interface NewsAPI {
    @GET("everything")
    suspend fun getEverything(
        @QueryMap options: Map<String, String>
    ) : Response<GetEverythingResponse>

    @GET("top-headlines")
    suspend fun getHeadlines(
        @QueryMap options: Map<String, String>
    ) : Response<GetHeadlinesResponse>

    @GET("top-headlines/sources")
    suspend fun getSources(
        @QueryMap options: Map<String, String>
    ) : Response<GetSourcesResponse>
}