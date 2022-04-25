package com.hungnpk.github.clients.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class User(
    val id: Int? = null,
    val username: String? = null,
    val avatarUrl: String? = null,
    val name: String? = null,
    val location: String? = null,
    val email: String? = null,
    val biography: String? = null,
    val blog: String? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val publicRepos: Int? = null,
    val publicGists: Int? = null,
    val company: String? = null
) : Parcelable
