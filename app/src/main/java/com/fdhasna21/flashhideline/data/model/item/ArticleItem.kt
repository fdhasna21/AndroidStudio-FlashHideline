package com.fdhasna21.flashhideline.data.model.item

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.parcelize.Parcelize


/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
class ArticleItem(
    val source: SourceItem = SourceItem(),
    val author: String = "",
    val title: String = "",
    val description: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val publishedAt: String = "",
    val content: String = ""
): Parcelable
