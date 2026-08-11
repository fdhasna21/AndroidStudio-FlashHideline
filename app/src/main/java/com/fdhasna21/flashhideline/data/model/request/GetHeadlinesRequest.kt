package com.fdhasna21.flashhideline.data.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@JsonIgnoreProperties(ignoreUnknown = true)
class GetHeadlinesRequest {
    var category: String? = null
    var country: String? = null
    var page: String? = null
    val pageSize: Int = 10
}