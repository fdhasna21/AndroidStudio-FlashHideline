package com.fdhasna21.flashhideline.ui.screen.fix.article

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.core.utils.Constants
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.data.model.request.GetEverythingRequest
import com.fdhasna21.flashhideline.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.fdhasna21.flashhideline.core.network.NetworkResult
import com.fdhasna21.flashhideline.data.model.response.GetEverythingResponse
import kotlinx.coroutines.launch

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@HiltViewModel
class ArticleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : BaseViewModel(savedStateHandle) {
    val source: SourceItem = savedStateHandle.get<SourceItem>(Constants.EXTRA.SOURCE_ITEM) ?: SourceItem()

    private val _articles = MutableStateFlow<List<ArticleItem>>(emptyList())
    val articles: StateFlow<List<ArticleItem>> = _articles.asStateFlow()

    private var currentPage = 1
    private val PAGE_SIZE = 10

    var canPaginate by mutableStateOf(false)
        private set

    var isPaginateLoading by mutableStateOf(false)
        private set

    init {
        getEverything()
    }

    /** Fetch from Cloud **/
    fun getEverything(q: String = "") {
        if (currentPage == 1) {
            launchNetwork(
                call = { fetchNewsFromRepository(q) },
                onSuccess = { response -> handleSuccessResponse(response) }
            )
        } else {
            isPaginateLoading = true
            viewModelScope.launch {
                when (val result = fetchNewsFromRepository(q)) {
                    is NetworkResult.Success -> {
                        isPaginateLoading = false
                        handleSuccessResponse(result.data)
                    }
                    is NetworkResult.Error -> {
                        isPaginateLoading = false
                        sendEffect(UiEffect.ShowToast(result.message))
                    }
                }
            }
        }
    }

    private suspend fun fetchNewsFromRepository(q: String): NetworkResult<GetEverythingResponse> {
        return newsRepository.getEverything(
            GetEverythingRequest().apply {
                this.sources = source.id
                this.q = q
                this.page = currentPage
                this.pageSize = PAGE_SIZE
            }
        )
    }

    private fun handleSuccessResponse(response: GetEverythingResponse) {
        val newArticles = response.articles ?: emptyList()
        val totalResults = response.totalResults ?: 0
        _articles.value = if (currentPage == 1) newArticles else _articles.value + newArticles
        val maxPage = (totalResults + PAGE_SIZE - 1) / PAGE_SIZE
        canPaginate = currentPage < maxPage && newArticles.isNotEmpty()
    }

    fun loadNextPage(q: String = "") {
        if (canPaginate && !isPaginateLoading) {
            currentPage++
            getEverything(q)
        }
    }

    fun onSearch(q: String) {
        currentPage = 1
        getEverything(q)
    }
}
