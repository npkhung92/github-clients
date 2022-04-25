package com.hungnpk.github.clients.domain.usecase.base

sealed class Result<out T> {

    data class Success<out T>(val result: T) : Result<T>()
    data class Failure(val error: Exception) : Result<Nothing>()

    val isSuccess
    get() = this is Success<T>
    val isFailed
    get() = this is Failure

    fun handle(successHandle: (T) -> Any, failedHandle: (Exception) -> Any): Any =
        when (this) {
            is Success -> successHandle(result)
            is Failure -> failedHandle(error)
        }
}
