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
            message: String,
            confirmTitle: String,
            confirmAction: (() -> Unit)? = null
        ) {
            MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setNeutralButton(confirmTitle) { dialog, _ ->
                    confirmAction?.invoke()
                    dialog.dismiss()
                }
                .create().show()
        }
    }
}