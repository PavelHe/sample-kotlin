package com.github.pavelvil.service

import org.springframework.stereotype.Service

@Service
open class DefaultKeyConverterService : KeyConverterService {

    val chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890-_".toCharArray() // нужно для генерации id
    val charToLong = (0..chars.size - 1)
            .map { i -> Pair(chars[i], i.toLong()) }
            .toMap() //для каждого символа создать пару, которая состоит из символа и его номера

    override fun keyToId(key: String) = key.map { c -> charToLong[c]!! }.fold(0L, { a, b -> a * chars.size + b })

    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()
        while (n != 0L) {
            builder.append(chars[(n % chars.size).toInt()])
            n /= chars.size
        }
        return builder.reverse().toString()
    }
}