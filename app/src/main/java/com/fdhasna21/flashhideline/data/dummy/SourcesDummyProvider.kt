package com.fdhasna21.flashhideline.data.dummy

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fdhasna21.flashhideline.R
import com.fdhasna21.flashhideline.core.base.BaseDummyProvider
import com.fdhasna21.flashhideline.data.model.item.ArticleItem
import com.fdhasna21.flashhideline.data.model.item.SourceItem
import com.fdhasna21.flashhideline.data.model.response.GetHeadlinesResponse
import com.fdhasna21.flashhideline.data.model.response.GetSourcesResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@Singleton
class SourcesDummyProvider @Inject constructor(
    @ApplicationContext context: Context,
    objectMapper: ObjectMapper
) : BaseDummyProvider<SourceItem, GetSourcesResponse>(context, objectMapper) {

    override fun getFullResponse(): GetSourcesResponse? {
        return loadFromRaw<GetSourcesResponse>(R.raw.dummy_sources_response)
    }

    override fun getListData(): List<SourceItem> {
        return getFullResponse()?.sources ?: emptyList()
    }

    override fun getSingleData(): SourceItem {
        return getListData().firstOrNull() ?: SourceItem()
    }
}