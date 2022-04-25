package com.hungnpk.github.clients.presentation.users

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.usecase.GetGithubUsersUseCase
import com.hungnpk.github.clients.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubUsersViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val githubUsersUseCase: GetGithubUsersUseCase) :
    BaseViewModel() {
    var usersLiveData: LiveData<PagingData<User>> = MutableLiveData()

    fun getKeyword() = savedStateHandle.get<String>(SEARCH_KEY).orEmpty()

    fun saveKeyword(keyword: String) {
        savedStateHandle[SEARCH_KEY] = keyword
    }

    fun loadUsers() {
        githubUsersUseCase(
            params = getKeyword(),
            scope = viewModelScope,
            onResult = { result ->
                result.handle(
                    ::handleUsers,
                    ::handleFailure
                )
            }
        )
    }

    private fun handleUsers(usersFlow: Flow<PagingData<User>>) {
        viewModelScope.launch {
            usersFlow.cachedIn(viewModelScope).collect {
                usersLiveData.set(it)
            }
        }
    }

    companion object {
        const val SEARCH_KEY = "keyword"
    }
}