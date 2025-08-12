package com.global.bean

import com.domain.repository.WiseSayingRepository

object SingletonScope {
    val wiseSayingRepository: WiseSayingRepository by lazy { WiseSayingRepository() }
}