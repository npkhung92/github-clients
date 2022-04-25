package com.hungnpk.github.clients.domain.repository

import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.usecase.base.Result

interface GithubUsersRepository {
    suspend fun getUsers(keyword: String): Result<List<User>>

    suspend fun getUserDetail(username: String): Result<User?>
}