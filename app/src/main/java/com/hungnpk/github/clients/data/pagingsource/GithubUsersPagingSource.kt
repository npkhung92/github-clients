package com.hungnpk.github.clients.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.hungnpk.github.clients.data.mapper.toUser
import com.hungnpk.github.clients.data.source.GithubService
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.response.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class GithubUsersPagingSource(
    private val keyword: String,
    private val apiService: GithubService
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = apiService.searchUsers(
                keyword = keyword,
                page = nextPageNumber,
                perPage = params.loadSize,
            )
            return if (response.isSuccessful) {
                val users = response.body()?.items ?: emptyList()
                val nextKey =
                    if (users.isEmpty() || users.size < params.loadSize) {
                        null
                    } else {
                        nextPageNumber + 1
                    }
                LoadResult.Page(
                    data = response.body()?.items?.map { userResponse -> userResponse.toUser() }
                        ?: emptyList(),
                    prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(
                    Exception(
                        response.message() ?: getErrorMessage(response = response.errorBody())
                    )
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private suspend fun getErrorMessage(response: ResponseBody?): String? {
        return try {
            val error = withContext(Dispatchers.IO) {
                response?.string()
            }
            Gson().fromJson(
                error,
                ErrorResponse::class.java
            ).message
        } catch (e: Exception) {
            null
        }
    }
}