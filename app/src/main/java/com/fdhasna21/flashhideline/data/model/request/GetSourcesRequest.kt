package com.fdhasna21.flashhideline.data.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@JsonIgnoreProperties(ignoreUnknown = true)
class GetSourcesRequest {
    var category: String? = null
    var country: String? = null
}
