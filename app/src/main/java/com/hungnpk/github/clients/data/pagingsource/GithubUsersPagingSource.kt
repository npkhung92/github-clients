package com.hungnpk.github.clients.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.hungnpk.github.clients.data.mapper.toUsers
import com.hungnpk.github.clients.data.source.GithubService
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.response.ErrorResponse
import okhttp3.ResponseBody
import java.nio.charset.Charset

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
            return when {
                response.isSuccessful -> {
                    val users = response.body()?.items ?: emptyList()
                    val nextKey =
                        if (users.isEmpty() || users.size < params.loadSize) {
                            null
                        } else {
                            nextPageNumber + 1
                        }
                    LoadResult.Page(
                        data = response.body()?.toUsers() ?: emptyList(),
                        prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                        nextKey = nextKey
                    )
                }
                response.code() == INVALID_KEYWORD_STATUS ->
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                else -> LoadResult.Error(
                    Exception(
                        getErrorMessage(response.errorBody())
                    )
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String? {
        return try {
            val clonedErrorBody = responseBody?.let {
                val source = it.source()
                val bufferedCopy = source.buffer.clone()
                bufferedCopy.readString(Charset.forName("UTF-8"))
            }
            Gson().fromJson(
                clonedErrorBody,
                ErrorResponse::class.java
            ).message
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val INVALID_KEYWORD_STATUS = 422
    }
}