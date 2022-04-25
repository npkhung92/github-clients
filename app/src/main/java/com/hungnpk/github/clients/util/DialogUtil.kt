package com.hungnpk.github.clients.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A helper class to show dialog.
 */
class DialogUtil {
    companion object {
        fun showSimpleDialog(
            context: Context,
            title: String,
            message: String
        ) {
            MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .create().show()
        }
    }
}