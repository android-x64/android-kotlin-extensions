package com.test.admin.testproj.tests.kotlin.extensions

// function takes lambda extension function on class Runnable as the parameter,
// it is known as lambda with receiver
inline fun runnable(crossinline body: Runnable.() -> Unit) = object : Runnable {
    override fun run() = body()
}