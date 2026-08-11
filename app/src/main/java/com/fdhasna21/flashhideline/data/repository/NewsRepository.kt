package com.fdhasna21.flashhideline.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fdhasna21.flashhideline.core.base.BaseRepository
import com.fdhasna21.flashhideline.core.network.NetworkResult
import com.fdhasna21.flashhideline.core.network.NetworkStateInfo
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.data.api.NewsAPI
import com.fdhasna21.flashhideline.data.dummy.EverythingDummyProvider
import com.fdhasna21.flashhideline.data.dummy.HeadlinesDummyProvider
import com.fdhasna21.flashhideline.data.dummy.SourcesDummyProvider
import com.fdhasna21.flashhideline.data.model.request.GetEverythingRequest
import com.fdhasna21.flashhideline.data.model.request.GetHeadlinesRequest
import com.fdhasna21.flashhideline.data.model.request.GetSourcesRequest
import com.fdhasna21.flashhideline.data.model.response.GetEverythingResponse
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import com.fdhasna21.flashhideline.data.model.response.GetSourcesResponse
import javax.inject.Inject

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

interface NewsRepository {
    suspend fun getEverything(request: GetEverythingRequest): NetworkResult<GetEverythingResponse>

    suspend fun getHeadlines(request: GetHeadlinesRequest): NetworkResult<GetHeadlinesResponse>

    suspend fun getSources(request: GetSourcesRequest): NetworkResult<GetSourcesResponse>
}

class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI,
    objectMapper: ObjectMapper,
    networkStateInfo: NetworkStateInfo
) : BaseRepository(networkStateInfo, objectMapper), NewsRepository {
    override suspend fun getEverything(request: GetEverythingRequest): NetworkResult<GetEverythingResponse> {
        return apiWithQuery(request) { queryMap ->
            newsAPI.getEverything(queryMap)
        }
    }

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

class DummyNewsRepositoryImpl @Inject constructor(
    private val headlinesDummyProvider: HeadlinesDummyProvider,
    private val sourcesDummyProvider: SourcesDummyProvider,
    private val everythingDummyProvider: EverythingDummyProvider
) : NewsRepository {

    override suspend fun getEverything(request: GetEverythingRequest): NetworkResult<GetEverythingResponse> {
        val response = everythingDummyProvider.getFullResponse()
        return if (response != null) {
            NetworkResult.Success(response)
        } else {
            NetworkResult.Error("Failed to parse dummy everything data".asUiText())
        }
    }

    override suspend fun getHeadlines(request: GetHeadlinesRequest): NetworkResult<GetHeadlinesResponse> {
        val response = headlinesDummyProvider.getFullResponse()
        return if (response != null) {
            NetworkResult.Success(response)
        } else {
            NetworkResult.Error("Failed to parse dummy headlines data".asUiText())
        }
    }

    override suspend fun getSources(request: GetSourcesRequest): NetworkResult<GetSourcesResponse> {
        val response = sourcesDummyProvider.getFullResponse()
        return if (response != null) {
            NetworkResult.Success(response)
        } else {
            NetworkResult.Error("Failed to parse dummy sources data".asUiText())
        }
    }
}