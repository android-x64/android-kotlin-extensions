package com.android.kotlin.extensions


/**
 * Extension function joins two functions, using the result of the first function as parameter
 * of the second one.
 */
infix fun <P1, R1, R2> ((P1) -> R1).then(f: (R1) -> R2): (P1) -> R2 {
    return { p1: P1 -> f(this(p1)) }
}

infix fun <R1, R2> (() -> R1).then(f: (R1) -> R2): () -> R2 {
    return { f(this()) }
}

/**
 * Extension function is the exact opposite of `then`, using the result of the second function
 * as parameter of the first one.
 */
infix fun <P1, R, P2> ((P1) -> R).compose(f: (P2) -> P1): (P2) -> R {
    return { p1: P2 -> this(f(p1)) }
}