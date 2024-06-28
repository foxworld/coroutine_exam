/*
 * Copyright© 2024. PETER HOON LEE, All rights reserved.
 * @Name : PETER HOON LEE
 * @Email : foxworld@gmail.com
 * @Created Date : 2024-6-28
 */

package com.hello.coroutine

import com.hello.coroutine.MainActivity.Companion.printWithThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SuspendingFunction {

    fun main() : Unit = runBlocking {
        printWithThread("START")
        printWithThread(calculateResult().toString())
        printWithThread("END")
    }

    // withContext(Dispatchers.Default) /  coroutineScope 는 비동기식을 동기식처럼  처리할수 있다
    private suspend fun calculateResult(): Int = withContext(Dispatchers.Default) {
    //private suspend fun calculateResult(): Int = coroutineScope {
        val num1 = async{
            delay(1000L)
            10
        }

        val num2 = async {
            delay(1000L)
            20
        }

        num1.await() + num2.await()
    }
}