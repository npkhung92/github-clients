package com.hungnpk.github.clients.presentation.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.usecase.GetGithubUserDetailUseCase
import com.hungnpk.github.clients.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubUserDetailViewModel @Inject constructor(private val githubUserDetailUseCase: GetGithubUserDetailUseCase) :
    BaseViewModel() {
    val userLiveData: LiveData<User?> = MutableLiveData()

    fun loadUserInformation(username: String? = null) {
        showLoading()
        githubUserDetailUseCase(
            params = username.orEmpty(),
            scope = viewModelScope,
            onResult = { result ->
                result.handle(
                    ::handleUserLoaded,
                    ::handleFailure
                )
                hideLoading()
            }
        )
    }

    private fun handleUserLoaded(user: User?) {
        userLiveData.set(user)
    }
}