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
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine4 {
     fun structuredConcurrency() {
        Log.i(TAG, "자식코루틴을 기다리다 예외가 발생하면 다른 자식 코루틴에게 취소요청 처리 #################")
        try {
            example1()
        } catch(e : Exception) {
            Log.e(TAG, e.toString())

        }

        Log.i(TAG, "runBlocking 없이 실행  #################")
        coroutineScopeExample1()

        Log.i(TAG, "suspend 넣고 join 함수사용  실행  #################")
        coroutineScopeExample2()
    }

     private fun example1() : Unit = runBlocking {
        //  예외가 부모로 전파되면 다른 자식 코루틴에게 취소 요청을 보낻다.
        launch {
            delay(600L)
            printWithThread("A")
        }

        launch {
            delay(500L)
            throw  IllegalArgumentException("코루틴 실패!!")
        }
    }

    private fun coroutineScopeExample1() {
        val job1 = CoroutineScope(Dispatchers.Default).launch {
            delay(100L)
            printWithThread("Job 1")
        }

        Thread.sleep(1500L)
    }

    private fun coroutineScopeExample2() = runBlocking{
        val job1 = CoroutineScope(Dispatchers.Default).launch {
            delay(100L)
            printWithThread("Job 1")
            coroutineContext + CoroutineName("이름")
        }

       job1.join()
    }

    class AsyncLogic{
        private val scope = CoroutineScope(Dispatchers.Default)

        fun doSomething() {
            scope.launch {
                // 무언가 코루틴이 시작되는 작업
            }
        }

        fun destory() {
            scope.cancel()
        }

    }



}