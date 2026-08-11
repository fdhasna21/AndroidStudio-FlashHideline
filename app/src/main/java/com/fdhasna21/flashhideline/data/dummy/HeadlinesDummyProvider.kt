package com.fdhasna21.flashhideline.data.dummy

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.base.BaseDummyProvider
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Singleton
class HeadlinesDummyProvider @Inject constructor(
    @ApplicationContext context: Context,
    objectMapper: ObjectMapper
) : BaseDummyProvider<ArticleItem, GetHeadlinesResponse>(context, objectMapper) {

    override fun getFullResponse(): GetHeadlinesResponse? {
        return loadFromRaw<GetHeadlinesResponse>(R.raw.dummy_headlines_response)
    }

    override fun getListData(): List<ArticleItem> {
        return getFullResponse()?.articles ?: emptyList()
    }

    override fun getSingleData(): ArticleItem {
        return getListData().firstOrNull() ?: ArticleItem()
    }
}