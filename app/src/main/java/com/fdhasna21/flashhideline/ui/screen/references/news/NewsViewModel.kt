package com.fdhasna21.flashhideline.ui.screen.references.news

import com.fdhasna21.flashhideline.core.base.BaseViewModel
import com.fdhasna21.flashhideline.core.di.DummyRepository
import com.fdhasna21.flashhideline.core.utils.component.asUiText
import com.fdhasna21.flashhideline.data.model.request.GetEverythingRequest
import com.fdhasna21.flashhideline.data.model.request.GetHeadlinesRequest
import com.fdhasna21.flashhideline.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Fernanda Hasna on 10/08/2026.
 * Updated by Fernanda Hasna on 11/08/2026.
 * **/

@HiltViewModel
class NewsViewModel @Inject constructor(
    @DummyRepository private val newsRepository: NewsRepository
) : BaseViewModel() {
    init {
        getEverything()
    }

    /** Fetch from Cloud **/
    fun getEverything(
        sortBy: String = "",
        q: String = ""
    ){
        launchNetwork (
            call = {
                newsRepository.getEverything(
                    GetEverythingRequest().apply {
                        this.sortBy = sortBy
                        this.q = q
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