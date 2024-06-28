/*
 * Copyright© 2024. PETER HOON LEE, All rights reserved.
 * @Name : PETER HOON LEE
 * @Email : foxworld@gmail.com
 * @Created Date : 2024-6-26
 */

package com.hello.coroutine

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()

        // 코루틴 기본
        //Coroutine1().basic()

        // 코루틴 심화
        //Coroutine2().inDepth()

        // 코루틴 예외처리
        //Coroutine3().exception()

        //Structured Concurrency
        //수많은 코루틴이 유실되거나 누수되지 않도록 보장
        //코드내에 에러가 유실되지 않고 적절히 보고될 수 있도록 보장
        //Coroutine4().structuredConcurrency()

        //Suspending fuction example
        //SuspendingFunction().main()
        //해당시간안에 끝나지 않으면 에러 또는 Null를 리턴한다
        //WithTimeout().main()
        //WithTimeoutOrNull().main()

        //Continuation Passing Style (CPS)
        ContinuationExample().main()


    }

    companion object {
        fun printWithThread(str : String) {
            Log.i(TAG, "[${Thread.currentThread().name}]: ${str}")
        }
    }
}