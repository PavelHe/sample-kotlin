package com.github.pavelvil.service

import com.github.pavelvil.model.Link
import com.github.pavelvil.repostiory.LinkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class DefaultKeyMapperService : KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService

    @Autowired
    lateinit var repository: LinkRepository

    // возвращаю ключе, по которому можно будет сгенерировать id объкта Link из БД
    @Transactional
    override fun add(link: String) = converter.idToKey(repository.save(Link(link)).id)

    // если в БД есть link, то возвращаем
    override fun getLink(key: String): KeyMapperService.Get {
        val result = repository.findById(converter.keyToId(key))
        return if (result.isPresent) {
            KeyMapperService.Get.Link(result.get().text)
        } else {
            KeyMapperService.Get.NotFound(key)
        }
    }
}