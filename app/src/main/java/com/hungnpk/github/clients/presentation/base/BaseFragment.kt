package com.hungnpk.github.clients.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hungnpk.github.clients.R
import com.hungnpk.github.clients.util.DialogUtil
import kotlinx.coroutines.Job

abstract class BaseFragment<B: ViewDataBinding>: Fragment() {
    lateinit var binding: B
    abstract val layout: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<B>(
            inflater,
            layout,
            container,
            false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViews()
    }

    abstract fun B.initViews()

    protected fun getDimenSize(@DimenRes dimenRes: Int) =
        resources.getDimensionPixelSize(dimenRes)
    protected fun getDimenOffset(@DimenRes dimenRes: Int) =
        resources.getDimensionPixelOffset(dimenRes)
    protected fun getColor(@ColorRes colorRes: Int) =
        ContextCompat.getColor(requireContext(), colorRes)

    protected fun goBack() {
        findNavController().navigateUp()
    }

    protected fun launchSuspend(suspendFunc: suspend () -> Unit): Job {
        return viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            suspendFunc()
        }
    }
    protected fun showErrorDialog(message: String?, retryAction: (() -> Unit)? = null) {
        DialogUtil.showSimpleDialog(
            context = requireContext(),
            title = getString(R.string.exception_title),
            message = message ?: getString(R.string.exception_general_msg),
            confirmTitle = getString(R.string.exception_retry_btn_label),
            confirmAction = retryAction,
            cancelTitle = getString(R.string.exception_cancel_btn_label)
        )
    }
}