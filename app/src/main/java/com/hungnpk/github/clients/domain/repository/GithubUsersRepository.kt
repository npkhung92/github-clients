package com.hungnpk.github.clients.domain.repository

import androidx.paging.PagingData
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.usecase.base.Result
import kotlinx.coroutines.flow.Flow

interface GithubUsersRepository {
    suspend fun getUsers(keyword: String): Result<Flow<PagingData<User>>>

    suspend fun getUserDetail(username: String): Result<User?>
}