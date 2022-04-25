package com.hungnpk.github.clients.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hungnpk.github.clients.data.mapper.toUser
import com.hungnpk.github.clients.data.pagingsource.GithubUsersPagingSource
import com.hungnpk.github.clients.data.source.GithubService
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.domain.usecase.base.Result
import com.hungnpk.github.clients.util.Constants.PAGING_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * This repository is responsible for
 * fetching data [User] from server
 * */
class GithubUsersRepositoryImpl @Inject constructor(
    private val apiService: GithubService
) : GithubUsersRepository {
    override suspend fun getUsers(keyword: String) : Result<Flow<PagingData<User>>> {
        return Result.Success(Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE
            ),
            pagingSourceFactory = {
                GithubUsersPagingSource(
                    keyword = keyword,
                    apiService = apiService
                )
            }
        ).flow.flowOn(Dispatchers.IO))
    }

    override suspend fun getUserDetail(username: String): Result<User?> {
        val response = apiService.getUserDetail(username = username)
        return if (response.isSuccessful) {
            Result.Success(response.body()?.toUser())
        } else {
            Result.Failure(Exception(response.message()))
        }
    }

}