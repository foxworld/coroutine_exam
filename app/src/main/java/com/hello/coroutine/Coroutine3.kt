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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine3 {

    fun exception() {
//        Log.i(TAG, "Root Coroutine example ##############################################")
//        example1()

//        Log.i(TAG, "Coroutine 예외처리 launch example ############################################")
//        example2_1()

//        Log.i(TAG, "Coroutine 예외처리 async example ############################################")
//        example2_2()

//        Log.i(TAG, "부모 자식 Coroutine 인 경우 예외처리 example ############################################")
//        example3_1()

//        Log.i(TAG, "부모 자식 Coroutine 인 경우 예외처리 받지 않은 example ############################################")
//        example3_2()

//        Log.i(TAG, "launch 예외처리 방법1(try catch finally) example ############################################")
//        example4_1()

        Log.i(TAG, "launch 예외처리 방법2(exceptionHandler 에 던지는 방법) example ############################################")
        example4_2()

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

    private fun example2_1() : Unit = runBlocking {
        // launch 는 예외가 발생하면 예외를 출력하고 코루틴을 종료
        val job = CoroutineScope(Dispatchers.Default).launch {
            throw IllegalArgumentException()
        }
        delay(1000L)
    }

    private fun example2_2() : Unit = runBlocking {
        // asycn 는 예외가 발생해도 예외를 출력하지 않음
        // 예외를 확인하려면 await()이 필요함
        val job = CoroutineScope(Dispatchers.Default).async {
            throw IllegalArgumentException()
        }
        delay(1000L)
        job.await()
    }

    private fun example3_1() : Unit = runBlocking {
        //  부모 자식관계인 경우 자식 에외가 부모한테 전달하여 에외를 출력한다
        val job = async {
            throw IllegalArgumentException()
        }
        delay(1000L)
    }

    private fun example3_2() : Unit = runBlocking {
        //  부모 자식관계인 경우이지만 예외처리를 받지 않는다
        val job = async(SupervisorJob()) {
            throw IllegalArgumentException()
        }
        delay(1000L)
    }

    private fun example4_1() : Unit = runBlocking {
        //  예외처리 방법1
        val job = launch {
            try {
                throw IllegalArgumentException()
            } catch(e : IllegalArgumentException) {
                printWithThread("정상 종료")
            }
        }
        delay(1000L)
    }

    private fun example4_2() : Unit = runBlocking {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            printWithThread("예외")
            throw throwable
        }

        // 주의사항
        // launch 에서만 적용 가능
        // 부모 코루틴이 있으면 동작하지 않음
        // CoroutineScope 는 새로운 부모 코루틴을 생성하는 함수
        val job = CoroutineScope(Dispatchers.Default).launch(exceptionHandler) {
            throw IllegalArgumentException()
        }

        delay(1000L)
    }


}