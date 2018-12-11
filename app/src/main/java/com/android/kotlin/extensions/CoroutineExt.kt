package com.android.kotlin.extensions

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Equivalent to [launch] but return [Unit] instead of [Job].
 *
 * Mainly for usage when you want to lift [launch] to return. Example:
 *
 * ```
 * override fun loadData() = launchSilent {
 *     // code
 * }
 * ```
 */
fun launchSilent(
        scope: CoroutineScope = GlobalScope,
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
) {
    scope.launch(context, start, block)
}
/**
 * Equivalent to [runBlocking] but return [Unit] instead of [T].
 *
 * Mainly for usage when you want to lift [runBlocking] to return. Example:
 *
 * ```
 * override fun loadData() = runBlockingSilent {
 *     // code
 * }
 * ```
 */
fun <T> runBlockingSilent(context: CoroutineContext = EmptyCoroutineContext,
                          block: suspend CoroutineScope.() -> T) {
    runBlocking(context, block)
}

fun <T> Deferred<T>.then(scope: CoroutineScope = GlobalScope, uiFun: (T) -> Unit)
        = launchSilent(scope) { uiFun(this@then.await()) }

fun <T> CoroutineScope.asyncIO(ioFun: () -> T) = async(Dispatchers.IO) { ioFun() }

fun <T> CoroutineScope.asyncIO(ioFun: () -> T, uiFun: (T) -> Unit) = asyncIO(ioFun).then(this, uiFun)