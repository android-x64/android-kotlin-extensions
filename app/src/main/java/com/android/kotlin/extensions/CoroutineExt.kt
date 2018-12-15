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

fun <T, E> CoroutineScope.asyncIO(ioFun1: () -> T, ioFun2: () -> E, uiFun: (T, E) -> Unit) {
    val d1 = asyncIO(ioFun1)
    val d2 = asyncIO(ioFun2)
    launchSilent(this) { uiFun(d1.await(), d2.await()) }
}

fun <T, E, K> CoroutineScope.asyncIO(ioFun1: () -> T, ioFun2: () -> E, ioFun3: () -> K, uiFun: (T, E, K) -> Unit) {
    val d1 = asyncIO(ioFun1)
    val d2 = asyncIO(ioFun2)
    val d3 = asyncIO(ioFun3)
    launchSilent(this) { uiFun(d1.await(), d2.await(), d3.await()) }
}

fun <T, E, K, D> CoroutineScope.asyncIO(ioFun1: () -> T, ioFun2: () -> E, ioFun3: () -> K, ioFun4: () -> D,
                                        uiFun: (T, E, K, D) -> Unit) {
    val d1 = asyncIO(ioFun1)
    val d2 = asyncIO(ioFun2)
    val d3 = asyncIO(ioFun3)
    val d4 = asyncIO(ioFun4)
    launchSilent(this) { uiFun(d1.await(), d2.await(), d3.await(), d4.await()) }
}

fun <T, E, K, D, U> CoroutineScope.asyncIO(ioFun1: () -> T, ioFun2: () -> E, ioFun3: () -> K, ioFun4: () -> D,
                                           ioFun5: () -> U, uiFun: (T, E, K, D, U) -> Unit) {
    val d1 = asyncIO(ioFun1)
    val d2 = asyncIO(ioFun2)
    val d3 = asyncIO(ioFun3)
    val d4 = asyncIO(ioFun4)
    val d5 = asyncIO(ioFun5)
    launchSilent(this) { uiFun(d1.await(), d2.await(), d3.await(), d4.await(), d5.await()) }
}

fun <T, E, K, D, U, F> CoroutineScope.asyncIO(ioFun1: () -> T, ioFun2: () -> E, ioFun3: () -> K, ioFun4: () -> D,
                                              ioFun5: () -> U, ioFun6: () -> F, uiFun: (T, E, K, D, U, F) -> Unit) {
    val d1 = asyncIO(ioFun1)
    val d2 = asyncIO(ioFun2)
    val d3 = asyncIO(ioFun3)
    val d4 = asyncIO(ioFun4)
    val d5 = asyncIO(ioFun5)
    val d6 = asyncIO(ioFun6)
    launchSilent(this) { uiFun(d1.await(), d2.await(), d3.await(), d4.await(), d5.await(), d6.await()) }
}