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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine2 {

    fun inDepth() {
//        Log.i(TAG, "Coroutine cancel 적용되는 example ##############################################")
//        example1()
//
//        Log.i(TAG, "Coroutine cancel 적용 안되는 example ############################################")
//        example2_1()

//        Log.i(TAG, "Coroutine cancel 적용 될 수 있도록 수정한 example ###############################")
//        example2_2()

        Log.i(TAG, "Coroutine cancel CancellationException 예외처리시 취소 적용 안되는 example #######")
        example2_3()

    }

    private fun example1() = runBlocking {
        val job1 = launch {
            delay(1000L) // delay는 suspend 함수이므로 코루틴이 적용되는 함수 // 협력하는 함수
            printWithThread("Job 1")
        }

        val job2 = launch {
            delay(1000L)
            printWithThread("Job 2")
        }

        delay(100L)
        job1.cancel()
    }

    private fun example2_1() = runBlocking {
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

    private fun example2_2() = runBlocking {
        // 메인스레드에서 동작하지 않게 하고 다른 스레드에서 실행되도록 수정
        val job = launch(Dispatchers.Default) {
            var i = 1
            var nextPrintTime = System.currentTimeMillis()

            // 아래 로직은 코루틴이 적용 안되는 함수로 무한루프 작업
            while(i <= 5) {
                if(nextPrintTime < System.currentTimeMillis()) {
                    printWithThread("${i++}번째 출력!!")
                    nextPrintTime += 1000L
                }

                if(!isActive) {
                    throw CancellationException()
                }
            }
        }

        delay(100L)
        job.cancel()
    }

    private fun example2_3() : Unit = runBlocking {
        val job = launch {
            try {
                delay(1000L)
            } catch(e : CancellationException) {
                // 아무처리도 안함
            }

            printWithThread("delay에 의해 취소되지 않았다!")
        }

        delay(100L)
        printWithThread("취소 시작")
        job.cancel()
    }

}