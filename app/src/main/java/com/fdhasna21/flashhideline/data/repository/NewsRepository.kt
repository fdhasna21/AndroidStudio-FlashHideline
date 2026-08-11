package com.fdhasna21.flashhideline.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fdhasna21.flashhideline.core.base.BaseRepository
import com.fdhasna21.flashhideline.core.network.NetworkResult
import com.fdhasna21.flashhideline.core.network.NetworkStateInfo
import com.fdhasna21.flashhideline.data.api.NewsAPI
import com.fdhasna21.flashhideline.data.model.request.GetHeadlinesRequest
import com.fdhasna21.flashhideline.data.model.request.GetSourcesRequest
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import com.fdhasna21.flashhideline.data.model.response.GetSourcesResponse
import javax.inject.Inject

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

interface NewsRepository {
    suspend fun getHeadlines(request: GetHeadlinesRequest): NetworkResult<GetHeadlinesResponse>

    suspend fun getSources(request: GetSourcesRequest): NetworkResult<GetSourcesResponse>
}

class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    objectMapper: ObjectMapper,
    networkStateInfo: NetworkStateInfo
) : BaseRepository(networkStateInfo, objectMapper), NewsRepository {
    override suspend fun getHeadlines(request: GetHeadlinesRequest): NetworkResult<GetHeadlinesResponse> {
        return apiWithQuery(request) { queryMap ->
            newsAPI.getHeadlines(queryMap)
        }
    }

    override suspend fun getSources(request: GetSourcesRequest): NetworkResult<GetSourcesResponse> {
        return apiWithQuery(request) { queryMap ->
            newsAPI.getSources(queryMap)
        }
    }

}