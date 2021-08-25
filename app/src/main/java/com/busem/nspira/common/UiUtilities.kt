package com.busem.nspira.common

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.toast(message: String, isLong: Boolean = false) {
    Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String, isLong: Boolean = false) {
    requireActivity().toast(message, isLong)
}

fun View.show() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.hide() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.toggleVisibility(shouldShow: Boolean? = null) {
    when (shouldShow) {
        true -> show()
        false -> hide()
        null -> {
            visibility = if (visibility != View.VISIBLE) View.VISIBLE
            else View.GONE
        }
    }
}