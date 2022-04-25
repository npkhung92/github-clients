package com.hungnpk.github.clients.util

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * A helper class to show images.
 */
class ImageUtil {
    companion object {
        fun showAvatar(imageView: ImageView, url: String?) {
            Glide.with(imageView.context)
                .load(url)
                .circleCrop()
                .into(imageView)
        }
    }
}