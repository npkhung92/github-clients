package com.hungnpk.github.clients.presentation.userdetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.hungnpk.github.clients.R
import com.hungnpk.github.clients.databinding.FragmentUserDetailBinding
import com.hungnpk.github.clients.presentation.base.BaseFragment
import com.hungnpk.github.clients.util.DialogUtil
import com.hungnpk.github.clients.util.ImageUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] class presenting detailed information of Github User.
 */
@AndroidEntryPoint
class GithubUserDetailFragment(override val layout: Int = R.layout.fragment_user_detail) : BaseFragment<FragmentUserDetailBinding>() {
    private val viewModel: GithubUserDetailViewModel by viewModels()
    private val args: GithubUserDetailFragmentArgs by navArgs()

    override fun FragmentUserDetailBinding.initViews() {
        this@GithubUserDetailFragment.viewModel.setupViewModel()
        bindViewModel()
        handleButton()
    }

    private fun FragmentUserDetailBinding.bindViewModel() {
        this.viewModel = this@GithubUserDetailFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }

    private fun FragmentUserDetailBinding.handleButton() {
        btnBack.setOnClickListener {
            goBack()
        }
    }

    private fun FragmentUserDetailBinding.showUserAvatar(avatarUrl: String?) {
        ImageUtil.showAvatar(ivUserAvatar, avatarUrl)
    }

    private fun GithubUserDetailViewModel.setupViewModel() {
        loadUserInformation(args.username)
        userLiveData.observe(viewLifecycleOwner) { user ->
            binding.showUserAvatar(user?.avatarUrl)
        }
        failure.observe(viewLifecycleOwner) {
            DialogUtil.showSimpleDialog(
                context = requireContext(),
                title = getString(R.string.exception_title),
                message = it.message ?: getString(R.string.exception_general_msg),
                confirmTitle = getString(R.string.exception_retry_btn_label)
            ) {
                loadUserInformation(args.username)
            }
        }
    }

}