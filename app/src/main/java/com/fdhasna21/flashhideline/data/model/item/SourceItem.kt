package com.fdhasna21.flashhideline.data.model.item

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.parcelize.Parcelize

/**
 * Created by Fernanda Hasna on 11/08/2026.
 * **/

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
class SourceItem (
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val url: String = "",
    val category: String = "",
    val language: String = "",
    val country: String = ""
) : Parcelable
