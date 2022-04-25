package com.hungnpk.github.clients.domain.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserListResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<UserDetailResponse>?,
    @SerializedName("total_count")
    val totalCount: Int?
)
