package com.hungnpk.github.clients.presentation.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.usecase.GetGithubUsersUseCase
import com.hungnpk.github.clients.presentation.base.BaseViewModel
import com.hungnpk.github.clients.presentation.base.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubUsersViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val githubUsersUseCase: GetGithubUsersUseCase) :
    BaseViewModel() {
    val usersLiveData: LiveData<List<User>> = MutableLiveData()

    fun getKeyword() = savedStateHandle.get<String>(SEARCH_KEY).orEmpty()

    fun saveKeyword(keyword: String) {
        savedStateHandle[SEARCH_KEY] = keyword
    }

    fun loadUsers() {
        showLoading()
        githubUsersUseCase(
            params = getKeyword(),
            scope = viewModelScope,
            onResult = { result ->
                result.handle(
                    ::handleUsers,
                    ::handleFailure
                )
                hideLoading()
            }
        )
    }

    private fun handleUsers(users: List<User>) {
        usersLiveData.set(users)
    }

    companion object {
        const val SEARCH_KEY = "keyword"
    }
}