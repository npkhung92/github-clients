package com.hungnpk.github.clients.domain.usecase

import androidx.paging.PagingData
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.domain.usecase.base.Result
import com.hungnpk.github.clients.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGithubUsersUseCase @Inject constructor(
    private val repository: GithubUsersRepository
): UseCase<Flow<PagingData<User>>, String>() {
    override suspend fun run(params: String): Result<Flow<PagingData<User>>> = repository.getUsers(params)
}