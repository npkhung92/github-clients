package com.hungnpk.github.clients.data.mapper

import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.response.UserDetailResponse
import com.hungnpk.github.clients.domain.response.UserListResponse

fun UserListResponse.toUsers(): List<User> {
    return this.items?.map { user ->
        User(
            id = user.id,
            username = user.login,
            avatarUrl = user.avatarUrl
        )
    } ?: emptyList()
}

fun UserDetailResponse.toUser(): User =
    User(
        id = id,
        username = login,
        avatarUrl = avatarUrl,
        name = name,
        location = location,
        email = email,
        blog = blog,
        biography = bio,
        followers = followers,
        following = following,
        publicGists = publicGists,
        publicRepos = publicRepos,
        company = company
    )