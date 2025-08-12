package com.global.util

import com.domain.entity.WiseSaying
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JsonUtil {
    private val json = Json { prettyPrint = true }

    fun saveWiseSayingToJsonFile(wiseSaying: WiseSaying) {
        val directory = File("db/wiseSaying")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val filePath = "db/wiseSaying/${wiseSaying.id}.json"
        val file = File(filePath)

        val jsonString = json.encodeToString(wiseSaying)

        file.writeText(jsonString)
    }

    fun saveLastIdToJsonFile(lastId: Int) {
        val directory = File("db/wiseSaying")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val filePath = "db/wiseSaying/lastId.json"
        val file = File(filePath)

        val jsonString = json.encodeToString( lastId)

        file.writeText(jsonString)
    }

    fun deleteWiseSaying(id: Int) {
        val filePath = "db/wiseSaying/$id.json"
        val file = File(filePath)

        if (file.exists()) {
            file.delete()
        }
    }

    fun saveWiseSayingsToFile(wiseSayingList: List<WiseSaying>) {
        val filePath = "db/wiseSaying/data.json"
        val file = File(filePath)

        val jsonString = json.encodeToString(wiseSayingList)

        file.writeText(jsonString)
    }

    fun loadWiseSayingsFromDirectory(wiseSayings: MutableList<WiseSaying>) {
        val directoryPath = "db/wiseSaying"
        val directory = File(directoryPath)

        if (!directory.exists() || !directory.isDirectory) {
            println("$directoryPath 디렉터리가 존재하지 않거나 올바른 디렉터리가 아닙니다. 빈 리스트로 시작합니다.")
            return
        }

        val files = directory.listFiles()
        if (files.isNullOrEmpty()) {
            println("디렉터리에 명언 파일이 없습니다. 빈 리스트로 시작합니다.")
            return
        }

        files.filter { it.isFile && it.extension == "json" && it.name != "data.json" && it.name != "lastId.json" }
            .forEach { file ->
                try {
                    // 3. 파일 내용을 읽고 WiseSaying 객체로 역직렬화
                    val jsonString = file.readText()
                    val wiseSaying: WiseSaying = json.decodeFromString(jsonString)
                    wiseSayings.add(wiseSaying)
                } catch (e: Exception) {
                    println("파일 ${file.name} 읽기 또는 역직렬화 중 오류가 발생했습니다: ${e.message}")
                }
            }

        println("총 ${wiseSayings.size}개의 명언을 불러왔습니다.")
    }

    fun clearJsonFiles() {
        val directory = File("db/wiseSaying")
        if (directory.exists()) {
            directory.deleteRecursively()
        }
    }

    fun loadLastIdFromJsonFile(): Int {
        val filePath = "db/wiseSaying/lastId.json"
        val file = File(filePath)

        if (!file.exists()) {
            return 0
        }

        val jsonString = file.readText()
        return json.decodeFromString(jsonString)
    }
}