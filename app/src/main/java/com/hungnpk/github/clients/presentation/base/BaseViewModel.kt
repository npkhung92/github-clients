package com.hungnpk.github.clients.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val failure: SingleLiveData<Exception> = SingleLiveData()
    val loading: LiveData<Boolean> = MutableLiveData(false)

    fun <T> LiveData<T>.post(data: T) = (this as MutableLiveData<T>).postValue(data)

    fun <T> LiveData<T>.set(data: T) {
        (this as MutableLiveData<T>).value = data
    }

    protected fun handleFailure(exception: Exception) {
        failure.set(exception)
    }

    protected fun showLoading() = loading.set(true)
    protected fun hideLoading() = loading.set(false)
}