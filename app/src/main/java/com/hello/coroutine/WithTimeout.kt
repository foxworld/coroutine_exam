/*
 * Copyright© 2024. PETER HOON LEE, All rights reserved.
 * @Name : PETER HOON LEE
 * @Email : foxworld@gmail.com
 * @Created Date : 2024-6-28
 */

package com.hello.coroutine

import com.hello.coroutine.MainActivity.Companion.printWithThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class WithTimeout {
    fun main() : Unit = runBlocking {
        val result : Int = withTimeout(1000L) {
            delay(1500L)
            10 + 20
        }
        printWithThread(result.toString())
    }
}

class WithTimeoutOrNull {
    fun main() : Unit = runBlocking {
        val result : Int? = withTimeoutOrNull(1000L) {
            delay(1500L)
            10 + 20
        }
        printWithThread(result.toString())
    }
}