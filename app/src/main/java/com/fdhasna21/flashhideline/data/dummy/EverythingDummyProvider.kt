package com.fdhasna21.flashhideline.data.dummy

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.base.BaseDummyProvider
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.data.model.response.GetEverythingResponse
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Singleton
class EverythingDummyProvider @Inject constructor(
    @ApplicationContext context: Context,
    objectMapper: ObjectMapper
) : BaseDummyProvider<ArticleItem, GetEverythingResponse>(context, objectMapper) {

    override fun getFullResponse(): GetEverythingResponse? {
        return loadFromRaw<GetEverythingResponse>(R.raw.dummy_everything_response)
    }

    override fun getListData(): List<ArticleItem> {
        return getFullResponse()?.articles ?: emptyList()
    }

    override fun getSingleData(): ArticleItem {
        return getListData().firstOrNull() ?: ArticleItem()
    }
}