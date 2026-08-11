package com.fdhasna21.flashhideline.ui.screen.sources

import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.data.model.request.GetEverythingRequest
import com.fdhasna21.flashhideline.data.model.request.GetHeadlinesRequest
import com.fdhasna21.flashhideline.data.model.request.GetSourcesRequest
import com.fdhasna21.flashhideline.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : BaseViewModel() {
    init {
        getSources()
    }

    fun getSources(
        category: String = "",
        country: String = ""
    ) {
        launchNetwork (
            call = {
                newsRepository.getSources(
                    GetSourcesRequest().apply {
                        this.country = country
                        this.category = category
                    }
                )
            },
            onSuccess = { response ->
                sendEffect(
                    UiEffect.ShowToast(
                        "Berhasil memuat ${response.sources.size ?: 0} berita".asUiText()
                    )
                )
            }
        )
    }
}