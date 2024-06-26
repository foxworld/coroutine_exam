/*
 * Copyright© 2024. PETER HOON LEE, All rights reserved.
 * @Name : PETER HOON LEE
 * @Email : foxworld@gmail.com
 * @Created Date : 2024-6-26
 */

package com.hello.coroutine

import android.content.ContentValues.TAG
import android.util.Log
import com.hello.coroutine.MainActivity.Companion.printWithThread
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine2 {

    fun inDepth() {
        Log.i(TAG, "Coroutine cancel 적용되는 example ##############################################")
        example1()

        Log.i(TAG, "Coroutine cancel 적용 안되는 example ############################################")
        example2()

    }

    private fun example1() = runBlocking {
        val job = launch {
            delay(1000L)
            printWithThread("Job")
        }

        delay(100L)
        job.cancel()
    }

    private fun example2() = runBlocking {
        val job = launch {
            var i = 1
            var nextPrintTime = System.currentTimeMillis()

            // 아래 로직은 코루틴이 적용 안되는 함수로 무한루프 작업
            while(i <= 5) {
                if(nextPrintTime < System.currentTimeMillis()) {
                    printWithThread("${i++}번째 출력!!")
                    nextPrintTime += 1000L
                }
            }
        }

        delay(100L)
        job.cancel()

    }
}