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

class ContinuationExample {
    fun main() : Unit = runBlocking {
        val service = UserService()
        printWithThread(service.findUser(1L, null).toString())
    }
}

interface Continuation {
    suspend fun resumeWith(data : Any?)
}

class UserService {
    private val userProfileRepository = UserProfileRepository()
    private val userImageRepository = UserImageRepository()

    private abstract class FindUserContinuation() : Continuation {
        var label = 0
        var profile : Profile? = null
        var image : Image? = null
    }

    suspend fun findUser(userId : Long, continuation: Continuation?) : UserDto {
        val sm = continuation as? FindUserContinuation?: object : FindUserContinuation() {
            override suspend fun resumeWith(data: Any?) {
                when(label) {
                    0 -> {
                        profile = data as Profile
                        label = 1
                    }
                    1 -> {
                        image = data as Image
                        label = 2
                    }
                }
                findUser(userId, this)
            }
        }

        when(sm.label) {
            0 -> { // 0단계 - 초기시작
                printWithThread("프로필을 가져 오겠습니다!!")
                val profile = userProfileRepository.findProfile(userId, sm)
            }
            1 -> { // 1단계 - 1차 중단후 재시작
                printWithThread("이미지를 가져 오겠습니다!!")
                val image = userImageRepository.findImage(sm.profile!!, sm)
            }
        }
        return UserDto(sm.profile!!, sm.image!!)
    }
}

data class UserDto(
    val profile : Profile,
    val image : Image
)


class UserProfileRepository {
    suspend fun findProfile(userId: Long, continuation : Continuation) {
        delay(100L)
        continuation.resumeWith(Profile())
    }
}

class Profile

class UserImageRepository {
    suspend fun findImage(profile: Profile, continuation : Continuation) {
        delay(100L)
        continuation.resumeWith(Image())
    }
}

class Image
