package com.hungnpk.github.clients.domain.response

import androidx.annotation.Keep

@Keep
data class ErrorResponse(
    val message: String,
    val documentationUrl: String
)
