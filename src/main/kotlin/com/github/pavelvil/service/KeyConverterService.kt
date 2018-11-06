package com.github.pavelvil.service

// конвертер для id и ссылок
interface KeyConverterService {
    fun idToKey(id: Long): String

    fun keyToId(key: String): Long
}