package com.hungnpk.github.clients.presentation.users

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hungnpk.github.clients.R
import com.hungnpk.github.clients.databinding.FragmentUsersBinding
import com.hungnpk.github.clients.presentation.base.BaseFragment
import com.hungnpk.github.clients.presentation.common.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A [Fragment] class presenting a list of Github users.
 */
@AndroidEntryPoint
class GithubUsersFragment(override val layout: Int = R.layout.fragment_users) :
    BaseFragment<FragmentUsersBinding>() {

    private val viewModel: GithubUsersViewModel by viewModels()
    private var searchJob: Job? = null
    private val usersAdapter: GithubUserAdapter by lazy {
        GithubUserAdapter { selectedUser ->
            selectedUser.username?.let { name ->
                navigateToDetail(name)
            }
        }
    }

    override fun FragmentUsersBinding.initViews() {
        this@GithubUsersFragment.viewModel.setupViewModel()
        setupUserRecyclerView()
        setupSearchView()
        setupSwipeRefreshView()
        bindViewModel()
    }

    private fun FragmentUsersBinding.setupSearchView() {
        etSearchUsers.apply {
            doAfterTextChanged { text ->
                handleSearch(text.toString())
            }
        }
    }

    private fun FragmentUsersBinding.setupUserRecyclerView() {
        rvGithubUsers.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = usersAdapter
            val padding = getDimenSize(R.dimen.padding_default)
            addItemDecoration(
                MarginItemDecoration(
                    head = padding,
                    between = padding,
                    tail = padding,
                    side = padding
                )
            )
        }
    }

    private fun FragmentUsersBinding.bindViewModel() {
        this.viewModel = this@GithubUsersFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    private fun FragmentUsersBinding.setupSwipeRefreshView() {
        srlUsers.apply {
            setColorSchemeColors(getColor(R.color.ocean_magic_blue))
            setOnRefreshListener {
                handleSearch()
            }
        }
    }

    private fun FragmentUsersBinding.switchStateOfUsersView(isUsersEmpty: Boolean) {
        if (isUsersEmpty) {
            rvGithubUsers.isGone = true
            tvEmptyState.isVisible = true
        } else {
            tvEmptyState.isGone = true
            rvGithubUsers.isVisible = true
        }
    }

    private fun FragmentUsersBinding.checkAndStopRefreshing() {
        srlUsers.let {
            if (it.isRefreshing) {
                it.isRefreshing = false
            }
        }
    }

    private fun GithubUsersViewModel.setupViewModel() {
        usersLiveData.observe(viewLifecycleOwner) {
            binding.checkAndStopRefreshing()
            binding.switchStateOfUsersView(it.isEmpty())
            usersAdapter.submitList(it)
        }
    }

    private fun navigateToDetail(name: String) {
        findNavController().navigate(GithubUsersFragmentDirections.goToUserDetail(name))
    }

    private fun handleSearch(keyword: String? = null) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(SEARCH_DELAY)
            keyword?.let {
                viewModel.saveKeyword(it)
            }
            viewModel.loadUsers()
        }
    }

    companion object {
        private const val SEARCH_DELAY = 500L // unit: milliseconds
    }
}