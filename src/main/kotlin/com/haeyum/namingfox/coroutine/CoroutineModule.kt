package com.haeyum.namingfox.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object CoroutineModule {
    val ioScope = CoroutineScope(Dispatchers.Default)
    val thread = Thread()
}