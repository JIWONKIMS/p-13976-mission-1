package com.domain.service

import com.domain.entity.WiseSaying
import com.domain.repository.WiseSayingRepository

class WiseSayingService {
    private val wiseSayingRepository = WiseSayingRepository()

    fun write(content: String, author: String): Int {
        return wiseSayingRepository.save(content, author)
    }

    fun findAll(): List<WiseSaying> {
        return wiseSayingRepository.findAll().sortedByDescending { it.id }
    }

    fun search(id : Int): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun delete(id: Int) {
        wiseSayingRepository.remove(id)
    }

    fun update(wiseSaying: WiseSaying, content: String, author: String){
        wiseSaying.update(content, author)
        wiseSayingRepository.save(wiseSaying)
    }

    fun build(){
        wiseSayingRepository.build()
    }

    fun load(){
        wiseSayingRepository.loadWiseSayings()
    }

    fun findByKeyword(keywordType: String, keyword: String): List<WiseSaying> {
        return when (keywordType) {
            "author" -> wiseSayingRepository.findByAuthorLike(keyword).sortedByDescending { it.id }
            else -> wiseSayingRepository.findByContentLike(keyword).sortedByDescending { it.id }
        }
    }
}