package com.hungnpk.github.clients.presentation.common

import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visible")
fun View.visible(isVisible: Boolean?) {
    this.isVisible = isVisible == true
}

fun View.hideKeyboard() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        windowInsetsController?.hide(WindowInsets.Type.ime())
    } else {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}