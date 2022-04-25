package com.hungnpk.github.clients.data.repository

import com.hungnpk.github.clients.data.mapper.toUser
import com.hungnpk.github.clients.data.mapper.toUsers
import com.hungnpk.github.clients.data.source.GithubService
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.domain.usecase.base.Result
import javax.inject.Inject

/**
 * This repository is responsible for
 * fetching data [User] from server
 * */
class GithubUsersRepositoryImpl @Inject constructor(
    private val apiService: GithubService
): GithubUsersRepository {
    override suspend fun getUsers(keyword: String): Result<List<User>> {
        val response = apiService.searchUsers(keyword = keyword)
        return if (response.isSuccessful) {
            Result.Success(response.body()?.toUsers() ?: emptyList())
        } else {
            Result.Failure(Exception(response.message()))
        }
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