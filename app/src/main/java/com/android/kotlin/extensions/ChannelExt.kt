package com.android.kotlin.extensions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.SendChannel

fun <E> SendChannel<E>.safeOffer(value: E) = !isClosedForSend && try {
    offer(value)
} catch (e: CancellationException) {
    false
}