package com

import com.domain.controller.WiseSayingController
import com.global.Rq

class App {
    fun run (){
        val wiseSayingController = WiseSayingController()
        wiseSayingController.orderLoad()

        println("== 명언 앱 ==")

        while(true){
            print("명령) ")
            val input = readlnOrNull()!!.trim()

            val rq = Rq(input)

            when(rq.order){
                "종료" -> break;
                "등록" -> wiseSayingController.orderWrite()
                "목록" -> wiseSayingController.orderList(rq)
                "삭제" -> wiseSayingController.orderDelete(rq)
                "수정" -> wiseSayingController.orderUpdate(rq)
                "빌드" -> wiseSayingController.orderBuild()
                else -> println("알 수 없는 명령입니다.")
            }

        }
    }
}