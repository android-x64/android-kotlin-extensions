package com.android.kotlin.extensions

import android.support.design.widget.Snackbar
import android.view.View

fun <T> Array<T>.resize(newSize: Int, creator: (Int) -> T): Array<T> {
    val copiedArray = Arrays.copyOf(this, newSize)
    for (i in size until newSize) { copiedArray[i] = creator(i) }
    return copiedArray
}