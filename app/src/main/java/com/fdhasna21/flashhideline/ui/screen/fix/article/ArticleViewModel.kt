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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
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

    val searchQuery = MutableStateFlow("")

    private var currentPage = 1
    private val PAGE_SIZE = 10

    var canPaginate by mutableStateOf(false)
        private set

    var isInlineLoading by mutableStateOf(false)
        private set

    init {
        getEverything()
        viewModelScope.launch {
            searchQuery
                .drop(1)
                .debounce(500L)
                .distinctUntilChanged()
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    private fun performSearch(query: String) {
        currentPage = 1
        canPaginate = false
        fetchInlineData(query)
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun getEverything(q: String = searchQuery.value) {
        launchNetwork(
            call = { fetchNewsFromRepository(q) },
            onSuccess = { response -> handleSuccessResponse(response) }
        )
    }

    private fun fetchInlineData(query: String) {
        isInlineLoading = true
        viewModelScope.launch {
            when (val result = fetchNewsFromRepository(query)) {
                is NetworkResult.Success -> {
                    isInlineLoading = false
                    handleSuccessResponse(result.data)
                }
                is NetworkResult.Error -> {
                    isInlineLoading = false
                    if (currentPage > 1) currentPage--
                    sendEffect(UiEffect.ShowToast(result.message))
                }
            }
        }
    }

    private suspend fun fetchNewsFromRepository(q: String): NetworkResult<GetEverythingResponse> {
        return newsRepository.getEverything(
            GetEverythingRequest().apply {
                this.sources = source.id
                this.q = q.ifBlank { null }
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

    fun loadNextPage() {
        if (canPaginate && !isInlineLoading) {
            currentPage++
            fetchInlineData(searchQuery.value)
        }
    }
}
