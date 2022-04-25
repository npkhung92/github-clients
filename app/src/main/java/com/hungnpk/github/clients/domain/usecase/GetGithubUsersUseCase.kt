package com.hungnpk.github.clients.domain.usecase

import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.domain.usecase.base.Result
import com.hungnpk.github.clients.domain.usecase.base.UseCase
import javax.inject.Inject

class GetGithubUsersUseCase @Inject constructor(
    private val repository: GithubUsersRepository
): UseCase<List<User>, String>() {
    override suspend fun run(params: String): Result<List<User>> = repository.getUsers(params)
}