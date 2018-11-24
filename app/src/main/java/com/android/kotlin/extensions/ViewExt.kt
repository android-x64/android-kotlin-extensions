package com.android.kotlin.extensions

import android.support.design.widget.Snackbar
import android.view.View

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}