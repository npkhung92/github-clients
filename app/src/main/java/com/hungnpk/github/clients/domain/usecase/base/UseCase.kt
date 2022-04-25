package com.hungnpk.github.clients.domain.usecase.base

import kotlinx.coroutines.*

abstract class UseCase<out T, in P> where T : Any? {
    abstract suspend fun run(params: P): Result<T>

    operator fun invoke(
        params: P,
        scope: CoroutineScope = GlobalScope,
        onResult: (Result<T>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(
                withContext(Dispatchers.IO) {
                    run(params)
                }
            )
        }
    }
}