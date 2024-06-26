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
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import kotlin.system.measureTimeMillis

class Coroutine1 {

    fun basic() {
        Log.i(TAG, "launch / yield example ############################################")
        example0()

        Log.i(TAG, "runBlocking example ###############################################")
        example1()

        Log.i(TAG, "launch start 제어 example ##########################################")
        example2()

        Log.i(TAG, "launch cancel 제어 example #########################################")
        example3()

        Log.i(TAG, "launch 다중 동시 및 순차 실행 example ##################################")
        example4()

        Log.i(TAG, "async example #####################################################")
        example5()

        Log.i(TAG, "async 다중 실행 시간 측정 example #####################################")
        example6()
    }

    private fun example0() : Unit = runBlocking {
        printWithThread("START")
        launch {
            newRoutine()
        }
        yield() // 지금 코루틴을 중단하고 다른 코루틴이 실행되도록 한다(스레드를 양보한다)
        printWithThread("END")
    }
    private suspend fun newRoutine() {
        val num1 = 1
        val num2 = 2
        yield()
        printWithThread("${num1 + num2}")
    }


    private fun example1() {
        // runBlocking 안의 내부 함수가 끝나야지만 다음으로 진행된다
        // 그러므로 자주 사용하는것이 아니라 최초 함수에만 설정한다
        runBlocking {
            printWithThread("START")
            launch {
                delay(2_000L)
                //yield()
                printWithThread("LAUNCH END")
            }
        }
        printWithThread("END")
    }

    private fun example2() : Unit = runBlocking {
        printWithThread("START")
        val job = launch(start = CoroutineStart.LAZY) { // start 로 launch 를  동작 시킨다
            printWithThread("Hello launch")
        }
        delay((1000L))
        job.start()
        printWithThread("END")
    }

    private fun example3() : Unit = runBlocking {
        printWithThread("START")
        val job = launch {
            (1..5).forEach{
                printWithThread("launch foreach ${it}")
                delay(500L)
            }
        }

        delay((1000L))
        job.cancel()
        printWithThread("END")
    }

    private fun example4() : Unit = runBlocking {
        val job1 = launch {
            delay(1000L)
            printWithThread("Job 1")
        }
        // join() 이 없는 경우는 거의 동시에 실행해서 1.1초 정도 시간이 된다
        job1.join() // join 은 첫번째 launch 가 끝날때까지 기다린후 다음 launch 를 실행한디

        val job2 = launch{
            delay(1000L)
            printWithThread("Job 2")
        }
    }

    private fun example5() : Unit = runBlocking {
        // async 는 결과를 리턴 할수 있다
        val job = async {
            3 + 5
        }
        val result = job.await() // await : async 의 결과를 가져오는 함수
        printWithThread(result.toString())
    }

    private fun example6()  : Unit = runBlocking {
        val time1 = measureTimeMillis {
            val job1 = async { apiCall1() }
            val job2 = async { apiCall2() }
            printWithThread((job1.await() + job2.await()).toString())
        }
        printWithThread("소요 시간 : ${time1} ms")

        // 동기식 처리하는 example
        val time2 = measureTimeMillis {
            val job1 = async { apiCall1() }
            val job2 = async { apiCall2(job1.await()) }
            printWithThread((job2.await()).toString())
        }
        // 순차 처리로 인한 2초의 소요가 발생
        printWithThread("소요 시간 : ${time2} ms")

        // 유의사항
        // LAZY 옵션을 사용하면 job1 끝나고 다음 job2를 실행된다
        // 이것을 회피 할려면 job1.start(), job2.start() 를 넣어줘야 동시 실행된다
        val time3 = measureTimeMillis {
            val job1 = async(start = CoroutineStart.LAZY) { apiCall1() }
            val job2 = async(start = CoroutineStart.LAZY) { apiCall2() }

//            job1.start()
//            job2.start()
            printWithThread((job1.await() + job2.await()).toString())
        }
        printWithThread("소요 시간 : ${time3} ms")
    }

    private suspend fun apiCall1() : Int {
        delay(1000L)
        return 1
    }
    private suspend fun apiCall2() : Int {
        delay(1000L)
        return 2
    }
    private suspend fun apiCall2(num : Int) : Int {
        delay(1000L)
        return num + 2
    }

}