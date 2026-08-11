package com.fdhasna21.flashhideline.ui.screen.fix.source

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.core.base.BaseViewModel.UiEffect
import com.fdhasna21.flashhideline.core.di.DummyRepository
import com.fdhasna21.flashhideline.core.utils.Constants
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.data.model.request.GetSourcesRequest
import com.fdhasna21.flashhideline.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@HiltViewModel
class ArticleSourceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : BaseViewModel(savedStateHandle) {

    private val categoryKey: String = savedStateHandle?.get<String>(Constants.EXTRA.ARTICLE_CAT) ?: ""

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    private val _sources = MutableStateFlow<List<SourceItem>>(emptyList())
    val sources: StateFlow<List<SourceItem>> = combine(
            _sources,
            _searchQuery.debounce(300L)
        ) { sourcesList, query ->
            if (query.isBlank()) {
                sourcesList
            } else {
                sourcesList.filter { source ->
                    source.name.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        getSources(categoryKey)
    }

    fun getSources(
        category: String
    ) {
        launchNetwork (
            call = {
                newsRepository.getSources(
                    GetSourcesRequest().apply {
                        this.category = category
                    }
                )
            },
            onSuccess = { response ->
                _sources.value = response.sources ?: emptyList()
            }
        )
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}