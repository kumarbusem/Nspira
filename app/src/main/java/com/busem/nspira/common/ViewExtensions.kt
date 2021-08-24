package com.busem.nspira.common

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.busem.nspira.R
import com.google.android.material.snackbar.Snackbar


fun AppCompatImageView.setUrlSource(url: String?) {
    url?.let {
        fun imagePlaceholderResource(): Int {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.R.drawable.ic_menu_recent_history
            } else {
                R.drawable.image_load_placeholder
            }
        }

        fun imageFailureResource(): Int {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                android.R.drawable.ic_delete
            } else {
                R.drawable.image_load_failure
            }
        }


        // Making sure that the provided HTML_URL is valid before loading the image.
        if (url.isValidUrl()) {
            Glide.with(context).load(url).placeholder(imagePlaceholderResource()).into(this)
        } else {
            Glide.with(context).load(imageFailureResource()).into(this)
        }
    }
}


/**
 * Function to display a [Snackbar] with a desired [message]
 * for a desired [duration] in a [ViewGroup].
 */
fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, message, duration)
}