package com.global

class Rq(cmd: String) {
    val order: String
    private val params = mutableMapOf<String, String>()

    init {
        val cmdBits = cmd.split("?", limit = 2)

        order = cmdBits[0]

        if(cmdBits.size == 2) {
            val queryStr = cmdBits[1]
            val queryBits = queryStr.split("&")

            for(queryBit in queryBits) {
                val queryParamBits = queryBit.split("=", limit = 2)

                if(queryParamBits.size != 2) {
                    continue
                }

                val paramName = queryParamBits[0].trim()
                val paramValue = queryParamBits[1].trim()

                params[paramName] = paramValue
            }
        }
    }

    private fun getParamValue(name: String): String? {
        return params[name]
    }

    fun getParamValue(name: String, default: String): String {
        return getParamValue(name) ?: default
    }

    fun getParamValueAsInt(name: String, default: Int): Int {
        val paramValue = getParamValue(name) ?: return default

        return try {
            paramValue.toInt()
        } catch (e: NumberFormatException) {
            default
        }
    }
}