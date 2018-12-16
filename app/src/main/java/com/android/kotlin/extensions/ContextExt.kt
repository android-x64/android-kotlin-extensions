package com.android.kotlin.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle

fun <T> Context.showActivity(it: Class<T>, extras: Bundle.() -> Unit) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}