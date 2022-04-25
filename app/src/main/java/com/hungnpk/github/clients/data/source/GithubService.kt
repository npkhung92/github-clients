package com.hungnpk.github.clients.data.source

import com.hungnpk.github.clients.domain.response.UserDetailResponse
import com.hungnpk.github.clients.domain.response.UserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<UserListResponse>

    @GET("/users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): Response<UserDetailResponse>
}