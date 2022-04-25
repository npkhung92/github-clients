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
import androidx.navigation.fragment.findNavController

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
}