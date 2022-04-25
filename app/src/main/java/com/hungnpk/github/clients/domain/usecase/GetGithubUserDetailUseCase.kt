package com.hungnpk.github.clients.domain.usecase

import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.domain.usecase.base.Result
import com.hungnpk.github.clients.domain.usecase.base.UseCase
import javax.inject.Inject

class GetGithubUserDetailUseCase @Inject constructor(
    private val repository: GithubUsersRepository
): UseCase<User?, String>() {
    override suspend fun run(params: String): Result<User?> = repository.getUserDetail(params)
}