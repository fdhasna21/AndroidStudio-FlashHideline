package com.fdhasna21.flashhideline.data.model.request

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

class GetEverythingRequest {
    var sortBy: String? = null
    var q: String? = null
    var page: Int = 1
    val pageSize: Int = 10
}
