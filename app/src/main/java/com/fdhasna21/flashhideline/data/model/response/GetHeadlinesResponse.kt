package com.fdhasna21.flashhideline.data.model.response

import com.fdhasna21.flashhideline.core.base.BaseResponse
import com.fdhasna21.flashhideline.data.model.item.ArticleItem

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

class GetHeadlinesResponse : BaseResponse(){
    var totalResult: Int = 0
    var articles: List<ArticleItem> = emptyList()
}