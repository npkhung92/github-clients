package com.hungnpk.github.clients.presentation.users

import com.hungnpk.github.clients.domain.model.User

/**
 * An interface to interact between [GithubUserAdapter] and [GithubUsersFragment].
 */
fun interface OnGithubUserAdapterListener {
    fun viewDetail(user: User)
}