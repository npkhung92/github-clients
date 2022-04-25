package com.hungnpk.github.clients.presentation.common

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visible")
fun View.visible(isVisible: Boolean?) {
    this.isVisible = isVisible == true
}