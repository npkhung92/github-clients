package com.hungnpk.github.clients.presentation.users

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hungnpk.github.clients.domain.model.User

/**
 * A [ListAdapter] class for presenting Github Users data
 * in a [RecyclerView] located in [GithubUsersFragment].
 */
class GithubUserAdapter(
    private val adapterListener: OnGithubUserAdapterListener? = null
) : PagingDataAdapter<User, GithubUserViewHolder>(
    GithubUserDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder.create(parent).apply {
            adapterListener?.let { listener ->
                itemView.setOnClickListener {
                    val selectedItem = getItem(bindingAdapterPosition) ?: return@setOnClickListener
                    listener.viewDetail(selectedItem)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val selectedItem = getItem(position) ?: return
        holder.bind(selectedItem)
    }
}

private class GithubUserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.username == newItem.username
                && oldItem.avatarUrl == newItem.avatarUrl
    }

}