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
            confirmAction: (() -> Unit)? = null,
            cancelTitle: String,
            cancelAction: (() -> Unit)? = null
        ) {
            MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(confirmTitle) { dialog, _ ->
                    confirmAction?.invoke()
                    dialog.dismiss()
                }
                .setNegativeButton(cancelTitle) { dialog, _ ->
                    cancelAction?.invoke()
                    dialog.dismiss()
                }
                .create().show()
        }
    }
}