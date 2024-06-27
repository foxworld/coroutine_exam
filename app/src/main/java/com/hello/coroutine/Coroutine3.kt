/*
 * Copyright© 2024. PETER HOON LEE, All rights reserved.
 * @Name : PETER HOON LEE
 * @Email : foxworld@gmail.com
 * @Created Date : 2024-6-27
 */

package com.hello.coroutine

import android.content.ContentValues.TAG
import android.util.Log
import com.hello.coroutine.MainActivity.Companion.printWithThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine3 {

    fun exception() {
//        Log.i(TAG, "Root Coroutine example ##############################################")
//        example1()

        Log.i(TAG, "Coroutine 예외처리 example ############################################")
        example2()

    }

    private fun example1() = runBlocking {
        // Root Coroutine 생성방법
        // 1. runBlocking
        // 2. CoroutineScope

        val job1 = CoroutineScope(Dispatchers.Default).launch {
            delay(1000L) // delay는 suspend 함수이므로 코루틴이 적용되는 함수 // 협력하는 함수
            printWithThread("Job 1")
        }

        val job2 = CoroutineScope(Dispatchers.Default).launch {
            delay(1000L)
            printWithThread("Job 2")
        }
    }

    private fun example2() = runBlocking {
        val job = CoroutineScope(Dispatchers.Default).launch {
            throw IllegalArgumentException()
        }

        delay(1000L)
    }


}