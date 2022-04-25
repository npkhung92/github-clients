package com.hungnpk.github.clients.presentation.users

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.hungnpk.github.clients.R
import com.hungnpk.github.clients.databinding.FragmentUsersBinding
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.presentation.base.BaseFragment
import com.hungnpk.github.clients.presentation.common.MarginItemDecoration
import com.hungnpk.github.clients.util.DialogUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

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
        }.apply {
            launchSuspend {
                loadStateFlow.asLiveData().observe(viewLifecycleOwner) {
                    handleLoadState(it)
                }
            }
        }
    }

    override fun FragmentUsersBinding.initViews() {
        viewModel.setupViewModel()
        setupUserRecyclerView()
        setupSearchView()
        setupSwipeRefreshView()
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

    private fun FragmentUsersBinding.setupSwipeRefreshView() {
        srlUsers.apply {
            setColorSchemeColors(getColor(R.color.ocean_magic_blue))
            setOnRefreshListener {
                usersAdapter.refresh()
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

    private fun FragmentUsersBinding.toggleLoading(show: Boolean) {
        ivProgress.isVisible = show
    }

    private fun GithubUsersViewModel.setupViewModel() {
        usersLiveData.observe(viewLifecycleOwner) {
            handleUpdateUsersList(it)
        }
    }

    private fun handleUpdateUsersList(users: PagingData<User>) {
        binding.checkAndStopRefreshing()
        launchSuspend {
            usersAdapter.submitData(users)
        }
    }

    private fun handleLoadState(loadState: CombinedLoadStates) {
        binding.toggleLoading(loadState.refresh is LoadState.Loading)
        binding.switchStateOfUsersView(loadState.refresh is LoadState.NotLoading && usersAdapter.itemCount == 0)
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
        errorState?.let {
            showErrorDialog(it.error.message) {
                usersAdapter.retry()
            }
        }
    }

    private fun showErrorDialog(message: String?, retryAction: (() -> Unit)? = null) {
        DialogUtil.showSimpleDialog(
            context = requireContext(),
            title = getString(R.string.exception_title),
            message = message ?: getString(R.string.exception_general_msg),
            confirmTitle = getString(R.string.exception_retry_btn_label),
            confirmAction = retryAction
        )
    }

    private fun navigateToDetail(name: String) {
        findNavController().navigate(GithubUsersFragmentDirections.goToUserDetail(name))
    }

    private fun handleSearch(keyword: String? = null) {
        searchJob?.cancel()
        searchJob = launchSuspend {
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