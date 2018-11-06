package com.github.pavelvil.service

import com.github.pavelvil.model.Link
import com.github.pavelvil.repostiory.LinkRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class DefaultKetMapperServiceTest {

    @InjectMocks //все внешние переменные в DefaultKeyMapperService заменить на моки
    val service: KeyMapperService = DefaultKeyMapperService()

    @Mock
    lateinit var converter: KeyConverterService

    @Mock
    lateinit var repository: LinkRepository

    private val KEY: String = "AaBbCcDd";
    private val LINK_A: String = "http://www.google.com"
    private val LINK_B: String = "http://www.yahoo.com"
    // ключи в мапе в DefaultKeyMapperService
    private val KEY_A: String = "abc"
    private val KEY_B: String = "cde"
    // id в мапе в DefaultKeyMapperService
    private val ID_A: Long = 10000000L
    private val ID_B: Long = 10000001L

    private val LINK_OBJ_A: Link = Link(LINK_A, ID_A)
    private val LINK_OBJ_B: Link = Link(LINK_B, ID_B)

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(converter.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converter.idToKey(ID_A)).thenReturn(KEY_A)
        Mockito.`when`(converter.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_B)).thenReturn(KEY_B)

        //мокаю методы репозитория
        Mockito.`when`(repository.findById(anyLong())).thenReturn(Optional.empty())
        Mockito.`when`(repository.save(Link(LINK_A))).thenReturn(LINK_OBJ_A)
        Mockito.`when`(repository.save(Link(LINK_B))).thenReturn(LINK_OBJ_B)
        Mockito.`when`(repository.findById(ID_A)).thenReturn(Optional.of(LINK_OBJ_A))
        Mockito.`when`(repository.findById(ID_B)).thenReturn(Optional.of(LINK_OBJ_B))
    }


    @Test
    fun clientCanAddLinks() {
        val keyA = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))
        val keyB = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))
    }

    @Test
    fun clientCannotTakeLinkIfKeyNotFoundInService() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }
}