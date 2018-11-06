package com.github.pavelvil.service

import org.junit.Assert.*
import org.junit.Test
import java.util.*


class DefaultKeyConverterServiceTest {

    val service: KeyConverterService = DefaultKeyConverterService()

    @Test
    fun givenIdMustBeConvertableBothWaves() {
        val random = Random()
        for (i in 0..1000) {
            val initialId = Math.abs(random.nextLong()) // берём рандомное только положительное
            val key = service.idToKey(initialId) // генерирую новую ссылку
            val id = service.keyToId(key) // из key получаю id. он должен совпадать с тем, из которого сгенерировалась ссылка
            assertEquals(initialId, id)
        }
    }

}