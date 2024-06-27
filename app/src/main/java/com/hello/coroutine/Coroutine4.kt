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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Coroutine4 {
    fun structuredConcurrency() {
        Log.i(TAG, "자식코루틴을 기다리다 예외가 발생하면 다른 자식 코루틴에게 취소요청 처리 #################")
        example1()
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

}