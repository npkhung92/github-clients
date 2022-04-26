package com.hungnpk.github.clients.domain.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("documentation_url")
    val documentationUrl: String? = null
)
