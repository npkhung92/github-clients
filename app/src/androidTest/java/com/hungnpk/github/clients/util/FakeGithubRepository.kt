package com.hungnpk.github.clients.util

import androidx.paging.PagingData
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.domain.usecase.base.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGithubRepository : GithubUsersRepository {

    var mockUserList = mutableListOf<User>()

    override suspend fun getUsers(keyword: String): Result<Flow<PagingData<User>>> = Result.Success(
        flowOf(PagingData.from(if (keyword.isNotEmpty()) mockUserList.filter { it.username?.contains(keyword) == true } else emptyList()))
    )

    override suspend fun getUserDetail(username: String): Result<User?> = Result.Success(
        mockUserList.firstOrNull { it.username == username }
    )
}