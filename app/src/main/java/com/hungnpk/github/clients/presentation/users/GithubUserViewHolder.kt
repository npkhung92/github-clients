package com.hungnpk.github.clients.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hungnpk.github.clients.R
import com.hungnpk.github.clients.databinding.ViewUserItemBinding
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.util.ImageUtil

/**
 * A [RecyclerView.ViewHolder] class which describes an item view representing for Github User, using in [GithubUserAdapter].
 */
class GithubUserViewHolder private constructor(private val binding: ViewUserItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.showUserName(user.username)
        binding.showAvatar(user.avatarUrl)
    }

    private fun ViewUserItemBinding.showUserName(name: String?) {
        tvUserName.text = name.orEmpty()
    }

    private fun ViewUserItemBinding.showAvatar(imageUrl: String?) {
        ImageUtil.showAvatar(ivUserAvatar, imageUrl)
    }

    companion object {
        fun create(parent: ViewGroup): GithubUserViewHolder {
            return GithubUserViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.view_user_item,
                    parent,
                    false
                )
            )
        }
    }
}