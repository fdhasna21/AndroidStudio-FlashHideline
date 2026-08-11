package com.fdhasna21.flashhideline.feature.main

import androidx.lifecycle.viewModelScope
import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.core.network.NetworkResult
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.data.model.request.GetHeadlinesRequest
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import com.fdhasna21.flashhideline.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {
    init {
        getTopHeadlines()
    }

    fun getTopHeadlines(
        category: String = "",
        country: String = ""
    ) {
        launchNetwork (
            call = {
                newsRepository.getHeadlines(
                    GetHeadlinesRequest().apply {
                        this.country = country
                        this.category = category
                    }
                )
            },
            onSuccess = { response ->
                sendEffect(
                    UiEffect.ShowToast(
                        "Berhasil memuat ${response.articles.size ?: 0} berita".asUiText()
                    )
                )
            }
        )
    }
}