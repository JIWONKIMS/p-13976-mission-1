package com.domain.repository

import com.domain.entity.WiseSaying
import com.global.util.JsonUtil

class WiseSayingRepository(
) {
    private val wiseSayings = mutableListOf<WiseSaying>()
    private var id = 0
    private val jsonUtil = JsonUtil()

    fun save(content: String, author: String): Int {
        val wiseSaying = WiseSaying(++id, content, author)
        wiseSayings.add(wiseSaying)
        jsonUtil.saveWiseSayingToJsonFile(wiseSaying)
        jsonUtil.saveLastIdToJsonFile(id)
        return id
    }

    fun save(wiseSaying: WiseSaying) {
        val index = wiseSayings.indexOfFirst { it.id == wiseSaying.id }
        wiseSayings[index] = wiseSaying
        jsonUtil.saveWiseSayingToJsonFile(wiseSaying)
    }

    fun findAll(): List<WiseSaying> {
        return wiseSayings.toList()
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    fun remove(id: Int) {
        wiseSayings.removeIf { it.id == id }
        jsonUtil.deleteWiseSaying(id)
    }

    fun build(){
        jsonUtil.saveWiseSayingsToFile(wiseSayings)
    }

    fun loadWiseSayings() {
        wiseSayings.clear()
        jsonUtil.loadWiseSayingsFromDirectory(wiseSayings)
        id = jsonUtil.loadLastIdFromJsonFile()
    }

    fun clear() {
        wiseSayings.clear()
        id = 0
        jsonUtil.clearJsonFiles()
    }

    fun findByAuthorLike(authorLike: String): List<WiseSaying> {
        return wiseSayings.filter { it.author.contains(authorLike) }
    }

    fun findByContentLike(contentLike: String): List<WiseSaying> {
        return wiseSayings.filter { it.content.contains(contentLike) }
    }

}