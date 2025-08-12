package com.domain.controller

import com.domain.service.WiseSayingService
import com.global.Rq

class WiseSayingController {
    private val wiseSayingService = WiseSayingService()

    fun orderWrite() {
        print("명언 : ")
        val content = readlnOrNull()!!.trim()
        print("작가 : ")
        val author = readlnOrNull()!!.trim()

        val id = wiseSayingService.write(content, author)
        println("${id}번 명언이 등록되었습니다.")
    }

    fun orderList(rq: Rq) {
        val keywordType = rq.getParamValue("keywordType", "")
        val keyword = rq.getParamValue("keyword", "")
        val page = rq.getParamValue("page", "1").toIntOrNull() ?: 1

        if (keywordType.isNotEmpty() && keyword.isNotEmpty()) {
            if (!listOf("author", "content").contains(keywordType)) {
                println("검색타입은 'author' 또는 'content' 만 가능합니다.")
                return
            }
        }

        val filteredWiseSayings = if (keywordType.isNotEmpty() && keyword.isNotEmpty()) {
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
            println("----------------------")
            wiseSayingService.findByKeyword(keywordType, keyword)
        } else {
            wiseSayingService.findAll()
        }

        val itemsPerPage = 5
        val totalPages = (filteredWiseSayings.size + itemsPerPage - 1) / itemsPerPage
        val startIndex = (page - 1) * itemsPerPage
        val endIndex = (startIndex + itemsPerPage).coerceAtMost(filteredWiseSayings.size)

        if (page < 1 || page > totalPages && totalPages > 0) {
            println("존재하지 않는 페이지입니다.")
            return
        }

        val pagedWiseSayings = filteredWiseSayings.subList(startIndex, endIndex)

        println("번호 / 작가 / 명언")
        println("----------------------")
        pagedWiseSayings.forEach {
            println("${it.id} / ${it.author} / ${it.content}")
        }
        println("----------------------")

        print("페이지 : ")
        for (i in 1..totalPages) {
            if (i == page) {
                print("[$i] ")
            } else {
                print("$i ")
            }
        }
        println()
    }

    fun orderDelete(rq: Rq) {
        val id = rq.getParamValueAsInt("id", 0)

        if (id == 0) {
            println("삭제할 명언의 id를 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.search(id)

        if(wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
        }else{
            wiseSayingService.delete(id)
            println("${id}번 명언이 삭제되었습니다.")
        }
    }

    fun orderUpdate(rq: Rq){
        val id = rq.getParamValueAsInt("id", 0)

        if(id == 0) {
            println("수정할 명언의 id를 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayingService.search(id)

        if(wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
        }else{
            println("명언(기존) : ${wiseSaying.content}")
            print("명언 : ")
            val newContent = readlnOrNull()!!.trim()

            println("작가(기존) : ${wiseSaying.author}")
            print("작가 : ")
            val newAuthor = readlnOrNull()!!.trim()

            wiseSayingService.update(wiseSaying, newContent, newAuthor)
            println("${id}번 명언이 수정되었습니다.")
        }
    }

    fun orderBuild(){
        wiseSayingService.build()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }

    fun orderLoad(){
        wiseSayingService.load()
    }
}