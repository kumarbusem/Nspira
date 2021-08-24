package com.busem.nspira.common

import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

/**
 * Function to display a [Snackbar] with a desired [message]
 * for a desired [duration] in a [ViewGroup].
 */
fun View.snack(message: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, message, duration)
}