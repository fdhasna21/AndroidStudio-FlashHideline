package com.fdhasna21.flashhideline.data.model.request

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

class GetEverythingRequest {
    var sources: String? = null
    var q: String? = null
    var page: Int = 1
    var pageSize: Int = 10
    var sortBy: String? = null
}
